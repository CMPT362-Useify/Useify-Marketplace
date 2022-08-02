package com.sfu.useify.ui.productDetails

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sfu.useify.R

class ProductDetailActivity: AppCompatActivity() {
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product_details, menu)
        return true
    }

    fun changeTheme(view: View){
        val nightModeFlags = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

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