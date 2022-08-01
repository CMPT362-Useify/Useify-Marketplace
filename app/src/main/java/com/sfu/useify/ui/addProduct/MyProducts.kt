package com.sfu.useify.ui.addProduct

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.sfu.useify.R
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product

class MyProducts: AppCompatActivity() {

    private lateinit var titleTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var descriptiontv: TextView

    // Models
    val productsViewModel = productsViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_product_item)

        val productsList: MutableLiveData<List<Product>> = productsViewModel.getAllProducts()
        productsList.observe(this, {
            println("debug: product " + it)
        })

    }

    private fun initializeFields() {
        titleTV = findViewById(R.id.productTitle)
        priceTV = findViewById(R.id.productPrice)
        descriptiontv = findViewById(R.id.productDesc)

    }
}