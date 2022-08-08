package com.sfu.useify.ui.productDetails

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.sfu.useify.R
import com.sfu.useify.Util.calculateTimeSincePosted
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product
import com.sfu.useify.ui.chat.ChatActivity
import com.squareup.picasso.Picasso
import java.util.*

class ProductDetailActivity: AppCompatActivity() {
    private lateinit var productsViewModel: productsViewModel
    private lateinit var titleTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var sellerIdTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var productImageView: ImageView

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setTitle(R.string.product_details_title)

        // Setup share and back buttons in Action Bar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup TextViews
        titleTextView = findViewById<TextView>(R.id.textview_product_details_title)
        priceTextView = findViewById<TextView>(R.id.textview_product_details_price)
        dateTextView = findViewById<TextView>(R.id.textview_product_details_date)
        descriptionTextView = findViewById<TextView>(R.id.textview_product_details_description)
        sellerIdTextView = findViewById<TextView>(R.id.textview_product_details_seller_id)
        locationTextView = findViewById<TextView>(R.id.textview_product_details_location)
        productImageView = findViewById<ImageView>(R.id.imageview_product_details_photo)


        // Setup ViewModel and get productID
        val bundle: Bundle? = intent.extras
        productsViewModel = productsViewModel()
        val productID = bundle?.getString(resources.getString(R.string.key_product_clicked))


        // Get product item
        if (productID != null){
            productsViewModel.getProductByID(productID).observe(this) {
                updateProductDetails(it)
            }
        }else {
            // TODO: Show 'error: product not found' in some way
        }
    }

    private fun updateProductDetails(newProduct: Product?){
        if (newProduct == null)
            return
        product = newProduct
        titleTextView.text = newProduct.name
        descriptionTextView.text = newProduct.description
        priceTextView.text = String.format("$%.2f", newProduct.price)
        dateTextView.text = calculateTimeSincePosted(newProduct.createAt)
        sellerIdTextView.text = newProduct.sellerID
        Picasso.get().load(newProduct.image).into(productImageView)        // Setup location TextView link (to GMaps)
        updateLocation(newProduct.pickupLat, newProduct.pickupLong)
    }

    private fun updateLocation(latitude: Double, longitude: Double) {
        // TODO: TEMPORARY COORDINATES, DELETE LATER WHEN DB HAS PRODUCTS WITH VALID COORDINATES
        var latitude = 49.28887213454251
        var longitude = -123.1110637962786

        // Get address from coordinates
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses.size == 0){
            locationTextView.text = "No valid location provided"
            return
        }
        val address = addresses[0]
        var addressString: String = ""
        for (i in 0 .. address.maxAddressLineIndex)
            addressString += address.getAddressLine(i)

        // Make the textview a link to google maps for the given coordinates
        locationTextView.movementMethod = LinkMovementMethod.getInstance()
        locationTextView.text = HtmlCompat.fromHtml("<a href>${addressString}</a>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        locationTextView.setOnClickListener {
            onLocationClicked(it, latitude, longitude)
        }
    }

    private fun onLocationClicked(view: View, latitude: Double, longitude: Double){
        val gmmIntentUri: Uri = Uri.parse("http://maps.google.com/maps?q=loc:$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product_details, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle Option pressed
        when (item.itemId){
            android.R.id.home -> onBackPressed()
            R.id.menu_share_btn -> onSharePressed()
        }
        return true
    }

    private fun onSharePressed() {
        // TODO("implement a share button")
    }

    // Start chat intent with seller regarding given product
    fun onChatClicked(view: View){
        if (product == null)
            return
        val intent = Intent(this, ChatActivity::class.java)
        val bundle = Bundle()
        bundle.putString(resources.getString(R.string.key_chat_seller_id), product!!.sellerID)
        bundle.putString(resources.getString(R.string.key_chat_product_id), product!!.productID)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}