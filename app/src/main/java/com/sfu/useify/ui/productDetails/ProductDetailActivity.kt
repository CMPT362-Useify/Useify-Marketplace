package com.sfu.useify.ui.productDetails

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.sfu.useify.R
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product
import java.util.concurrent.Executors


class ProductDetailActivity : AppCompatActivity() {

    // TextView fields
    private lateinit var titleTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var imgIV: ImageView
    private lateinit var createdAtTV: TextView
    private lateinit var callBtn: ImageButton

    // general
    private var productId = ""
    private var createdDateTime = ""


    // Models
    val productsViewModel = productsViewModel();
    lateinit var myProduct: MutableLiveData<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setTitle(R.string.product_details_title)

        // Setup share and back buttons in Action Bar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
//        actionBar?.hide()

//        val locationLink = findViewById<TextView>(R.id.textview_product_details_location)
//        locationLink.movementMethod = LinkMovementMethod.getInstance()
//        locationLink.setLinkTextColor(Color.BLUE)


        initializeFields()

        // get product ID from intent
        val extras = intent.extras
        if (extras != null) {
            productId = extras.getString("productIdKey", "")
            myProduct = productsViewModel.getProductByID(productId)

            myProduct.observe(this) {
                if (it != null) {
                    setProductinView(it)
                }
                Log.d("myProduct: ", it.name)
                Toast.makeText(this, "product detail" + it.name, Toast.LENGTH_LONG).show()

            }
        }


    }

    private fun initializeFields() {
        titleTV = findViewById(R.id.textview_product_details_title)
        priceTV = findViewById(R.id.textview_product_details_price)
        descriptionTV = findViewById(R.id.textview_product_details_description)
        createdAtTV = findViewById(R.id.textview_product_details_date)
        imgIV = findViewById(R.id.imageview_product_details_photo)
        callBtn = findViewById(R.id.imagebutton_call)

        callBtn.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "8344814819")
            startActivity(dialIntent)
        }
    }

    private fun setProductinView(it: Product) {
        titleTV.setText(it.name)
        priceTV.setText("$" + it.price.toString())
        descriptionTV.setText(it.description)
        //createdAtTV.setText()

        if (it.image !== "") {
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            var image: Bitmap? = null
            executor.execute {
                val imageURL = it.image
                try {
                    val `in` = java.net.URL(imageURL).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                    // Only for making changes in UI
                    handler.post {
                        imgIV.setImageBitmap(image)
                    }

                    val params = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                    )
                    imgIV.setLayoutParams(params)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product_details, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Finish when back pressed
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        if (item.title == "Share"){
            // TODO: implement a share button
        }
        return true
    }

    fun onLocationClicked(view: View){
        val gmmIntentUri: Uri = Uri.parse("http://maps.google.com/maps?q=loc:49.27803091224779,-122.91947918547892")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}