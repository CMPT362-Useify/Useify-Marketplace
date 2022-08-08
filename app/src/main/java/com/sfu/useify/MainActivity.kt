package com.sfu.useify

import android.content.Intent
import android.content.res.Configuration

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sfu.useify.database.conversationsViewModel
import com.sfu.useify.resetPages.GeneralSettings
import com.sfu.useify.resetPages.ResetPassword
import com.sfu.useify.ui.addProduct.AddEditProductActivity
import com.sfu.useify.ui.addProduct.MyProducts
import com.sfu.useify.ui.browse.BrowseActivity
import com.sfu.useify.ui.categories.SearchCategoriesActivity
import com.sfu.useify.ui.chat.ChatMenuActivity
import com.sfu.useify.ui.productDetails.ProductDetailActivity
import com.sfu.useify.ui.authentication.signup.SignupActivity
import com.sfu.useify.ui.savedProducts.SavedProductsActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search ->{
                val intent = Intent(this, SearchCategoriesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_inbox ->{
                val intent = Intent(this, ChatMenuActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_add ->{
                val newScreen = Intent(this, AddEditProductActivity::class.java)
                startActivity(newScreen)
                return true
            }
            R.id.action_view_my_products ->{
                val newScreen = Intent(this, MyProducts::class.java)
                startActivity(newScreen)
                return true
            }
            R.id.action_saved_products ->{
                val newScreen = Intent(this, SavedProductsActivity::class.java)
                startActivity(newScreen)
                return true
            }
            R.id.action_explore ->{
                val newScreen = Intent(this, BrowseActivity::class.java)
                startActivity(newScreen)
                return true
            }
            R.id.action_settings -> {
                val newScreen = Intent(this, GeneralSettings::class.java)
                startActivity(newScreen)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}