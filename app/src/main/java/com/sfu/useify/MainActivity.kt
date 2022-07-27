package com.sfu.useify

import android.content.Intent

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sfu.useify.ui.addProduct.AddProductActivity
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

    fun onAddProductClicked(view : View){
        val newScreen = Intent(this, AddProductActivity::class.java)
        startActivity(newScreen)
    }

    fun onProductList(view : View){
        val newScreen = Intent(this, MyProducts::class.java)
        startActivity(newScreen)
    }
}