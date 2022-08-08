package com.sfu.useify

import android.content.Intent
import android.content.res.Configuration

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sfu.useify.resetPages.GeneralSettings
import com.sfu.useify.resetPages.ResetPassword
import com.sfu.useify.ui.addProduct.AddEditProductActivity
import com.sfu.useify.ui.addProduct.MyProducts
import com.sfu.useify.ui.browse.BrowseActivity
import com.sfu.useify.ui.categories.SearchCategoriesActivity
import com.sfu.useify.ui.productDetails.ProductDetailActivity
import com.sfu.useify.ui.signup.SignupActivity


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

    fun onSearchClicked(view: View) {
        val intent = Intent(this, SearchCategoriesActivity::class.java)
        startActivity(intent)
    }

    fun onDetailsClicked(view: View) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }


    fun onSignUpClicked(view : View){
        val newScreen = Intent(this, SignupActivity::class.java)
        startActivity(newScreen)
    }

    fun onExploreClicked(view: View){
        val newScreen = Intent(this, BrowseActivity::class.java)
        startActivity(newScreen)
    }

    fun onSettingsClicked(view: View){
        val newScreen = Intent(this, GeneralSettings::class.java)
        startActivity(newScreen)
    }

    fun onResetClicked(view: View){
        val newScreen = Intent(this, ResetPassword::class.java)
        startActivity(newScreen)
    }

    fun onAddProductClicked(view : View){
        val newScreen = Intent(this, AddEditProductActivity::class.java)
        startActivity(newScreen)
    }

    fun onProductList(view : View){
        val newScreen = Intent(this, MyProducts::class.java)
        startActivity(newScreen)
    }
}