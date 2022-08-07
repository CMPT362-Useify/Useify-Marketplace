package com.sfu.useify.ui.addProduct

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.sfu.useify.MainActivity
import com.sfu.useify.R
import com.sfu.useify.Util
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product
import java.io.File
import java.util.*


class AddEditProductActivity : AppCompatActivity() {

    private val AUTOCOMPLETE_REQUEST_CODE: Int = 1

    // editText fields
    private lateinit var titleET: EditText
    private lateinit var priceET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var pickUpLoctionET: EditText
//    private lateinit var pickUpLoctionET: AutoCompleteTextView
    private lateinit var categorySelect: Spinner
    private lateinit var pageTitle: TextView

    // buttons
    private lateinit var deleteBtn: Button
    private lateinit var addProductBtn: Button
    private lateinit var updateProductBtn: Button


    // TODO: get images and seller ID
    private var sellerID = "0"
    private var pickupLat = 0.0
    private var pickupLong = 0.0

    //product image
    private var imgUrl = ""
    private lateinit var profileImgOptions: Array<String>
    private lateinit var cameraResult: ActivityResultLauncher<Intent>
    private lateinit var galleryResult: ActivityResultLauncher<Intent>
    private lateinit var mImageView: ImageView
    private lateinit var mTempImgUri: Uri
    private val mTempImgName = "my_temp_img.jpg"
    private val mProductName = "my_profile.jpg"
    private lateinit var mProductImgUri: Uri
    private lateinit var bitmap: Bitmap
    private var productId = ""
    private var isImageChanged: Boolean = false


    // Models
    val productsViewModel = productsViewModel();
    lateinit var myProduct: MutableLiveData<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        //get products input fields
        initializeFields()

        // get product ID from intent
        val extras = intent.extras
        if (extras != null) {
            // edit item
            addProductBtn.visibility = GONE
            deleteBtn.visibility = VISIBLE
            updateProductBtn.visibility = VISIBLE
            pageTitle.text = "Edit Product"

            productId = extras.getString("productIdKey", "")
            myProduct = productsViewModel.getProductByID(productId)

            myProduct.observe(this) {
                if (it != null) {
                    setProductinView(it)
                }
            }
        } else {
            //add new
            pageTitle.text = "Add a Product"
            deleteBtn.visibility = GONE
            updateProductBtn.visibility = GONE
            addProductBtn.visibility = VISIBLE
        }

        setupLocationAutocomplete()

        // Photo widget setup
        productImgSetUp(savedInstanceState)

    }

    fun onLocationClicked(view: View) {
        // Add address fields that you need from the autocomplete fragment
        val fields: List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field
            .ADDRESS)
        val intent =
            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    private fun setupLocationAutocomplete() {
        pickUpLoctionET.isEnabled = false
        Places.initialize(applicationContext, "AIzaSyD3txXQ3Nz2SSWvm-Ay6PKzaRbX_bpUwcw")
        val placesApi = Places.createClient(this)
    }

    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    println("Debug: Place = " + place.name + ", "+ place.address)
                    pickUpLoctionET.setText("${place.name}")
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status: Status = Autocomplete.getStatusFromIntent(data)
                    println("Debug: " + status.statusMessage)
                }
                RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
        }
    }


    private fun setProductinView(product: Product) {
        titleET.setText(product.name)
        priceET.setText(product.price.toString())
        descriptionET.setText(product.description)

        categorySelect.setSelection(getIndex(categorySelect, product.category));

        var imgUrl = product.image
        if (imgUrl !== "") {
//            Util.loadImgInView(imgUrl, mImageView)

        }
    }

    private fun getIndex(spinner: Spinner, category: String): Int {
        var index = 0
        for (i in 0 until spinner.getCount()) {
            if (spinner.getItemAtPosition(i).equals(category)) {
                index = i
            }
        }
        return index
    }

    private fun initializeFields() {
        titleET = findViewById(R.id.productTitleEt)
        priceET = findViewById(R.id.productPriceEt)
        descriptionET = findViewById(R.id.productDescEt)
        pickUpLoctionET = findViewById(R.id.productLocationEt)
        categorySelect = findViewById(R.id.categorySpinner)
        mImageView = findViewById(R.id.productImgIv)
        deleteBtn = findViewById(R.id.deleteBtn)
        addProductBtn = findViewById(R.id.addProductBtn)
        pageTitle = findViewById(R.id.addProductTv)
        updateProductBtn = findViewById(R.id.updateProductBtn)
    }

    private fun setPrpductImg(imgName: String, imgUri: Uri) {
        if (File(getExternalFilesDir(null), imgName).exists()) {
            bitmap = Util.getBitmap(this, imgUri)
            mImageView.setImageBitmap(bitmap)
            isImageChanged = true
        }
    }

    private fun productImgSetUp(savedInstanceState: Bundle?) {
        // Credit to Professor Xing-Dong Yang: https://www.sfu.ca/~xingdong/Teaching/CMPT362/lecture14/lecture14.html
        Util.checkPermissions(this)
        val tempImgFile = File(getExternalFilesDir(null), mTempImgName)
        mTempImgUri = FileProvider.getUriForFile(this, "com.sfu.useify", tempImgFile)
        val profileImgFile = File(getExternalFilesDir(null), mProductName)
        mProductImgUri = FileProvider.getUriForFile(this, "com.sfu.useify", profileImgFile)
        if (savedInstanceState != null)
            setPrpductImg(mTempImgName, mTempImgUri)

        cameraResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                setPrpductImg(mTempImgName, mTempImgUri)


            }
        }

        galleryResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intentData = result.data
                val uri = intentData?.data!!
                Util.saveBitmapIntoDevice(this, mTempImgName, uri)
                setPrpductImg(mTempImgName, mTempImgUri)

            }
        }
        // end photo widget setup
    }

    fun onChangeProductImgClicked(view: View) {
        profileImgOptions = arrayOf("Take From Camera", "Select from Gallery")
        var intent: Intent

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Product Image")
        builder.setItems(profileImgOptions)
        { dialog, which ->
            if (profileImgOptions[which].equals("Take From Camera")) {
                intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mTempImgUri)
                cameraResult.launch(intent)
            } else {
                intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryResult.launch(intent)
            }
        }
        val profileImgPickerDlialog = builder.create()
        profileImgPickerDlialog.show()
    }

    fun onAddNewProductClicked(view: View) {

        val mProduct = Product(
            titleET.text.toString(),
            priceET.text.toString().toDouble(),
            imgUrl,
            descriptionET.text.toString(),
            sellerID,
            categorySelect.selectedItem.toString(),
            pickupLat,
            pickupLong
        )
        productsViewModel.addProductWithPhoto(mProduct, bitmap)

        Toast.makeText(this, "new product added", Toast.LENGTH_LONG).show()

        // back to main page
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    fun onCancelProductClicked(view: View) {
        Util.deleteImg(this, mTempImgName)
        finish()
    }

    fun onDeleteProductClicked(view: View) {
        if(productId !== ""){
            Toast.makeText(this, "remove product Id: " + productId, Toast.LENGTH_LONG).show()
            productsViewModel.deleteProduct(productId)
            finish()
        }
    }

    fun onUpdateProductClicked(view: View) {

        if (productId != "") {
            val mProduct = Product(
                titleET.text.toString(),
                priceET.text.toString().toDouble(),
                imgUrl,
                descriptionET.text.toString(),
                sellerID,
                categorySelect.selectedItem.toString(),
                pickupLat,
                pickupLong,
                productId
            )
            if (isImageChanged){
                productsViewModel.updateProductWithPhoto(productId,mProduct, bitmap)
                Toast.makeText(this, "product updated", Toast.LENGTH_LONG).show()
                finish()
            } else {
                productsViewModel.updateProduct(productId, mProduct)
                Toast.makeText(this, "product updated", Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }
}

