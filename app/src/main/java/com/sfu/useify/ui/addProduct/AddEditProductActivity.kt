package com.sfu.useify.ui.addProduct

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.sfu.useify.MainActivity
import com.sfu.useify.R
import com.sfu.useify.Util
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product
import java.io.File

class AddEditProductActivity : AppCompatActivity() {

    // editText fields
    private lateinit var titleET: EditText
    private lateinit var priceET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var pickUpLoctionET: EditText
    private lateinit var categorySelect: Spinner

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

    // Models
    val productsViewModel = productsViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        //get products input fields
        initializeFields()

        // Photo widget setup
        productImgSetUp(savedInstanceState)


    }

    private fun initializeFields() {
        titleET = findViewById(R.id.productTitleEt)
        priceET = findViewById(R.id.productPriceEt)
        descriptionET = findViewById(R.id.productDescEt)
        pickUpLoctionET = findViewById(R.id.productLocationEt)
        categorySelect = findViewById(R.id.categorySpinner)
        mImageView = findViewById(R.id.productImgIv)
    }

    private fun setPrpductImg(imgName: String, imgUri: Uri) {
        if (File(getExternalFilesDir(null), imgName).exists()) {
            bitmap = Util.getBitmap(this, imgUri)
            mImageView.setImageBitmap(bitmap)
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

        Toast.makeText(this, "product img " + mProductImgUri, Toast.LENGTH_LONG).show()

        val newProduct = Product(
            titleET.text.toString(),
            priceET.text.toString().toDouble(),
            imgUrl,
            descriptionET.text.toString(),
            sellerID,
            categorySelect.selectedItem.toString(),
            pickupLat,
            pickupLong
        )
        productsViewModel.addProductWithPhoto(newProduct, bitmap)
//        productsViewModel.addProduct(newProduct)

        Toast.makeText(this, "new product added", Toast.LENGTH_LONG).show()

        // back to main page
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)

    }

    fun onCancelProductClicked(view: View) {
        Util.deleteImg(this, mTempImgName)
        finish()
    }
}

