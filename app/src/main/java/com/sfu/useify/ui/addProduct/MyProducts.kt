package com.sfu.useify.ui.addProduct

import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.sfu.useify.R
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product
import com.sfu.useify.ui.browse.ProfileAdapter

class MyProducts: AppCompatActivity() {

    private lateinit var titleTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var descriptiontv: TextView
    private lateinit var imgIV: ImageView

    //general
    private lateinit var gridView: GridView
    private lateinit var titles: MutableList<String>
    private lateinit var price: MutableList<Float>
    private lateinit var ids: MutableList<String>
    private lateinit var images: IntArray
    private lateinit var productDatabase: productsViewModel
    private lateinit var myArrayList: MutableLiveData<List<Product>>

    // Models
    val productsViewModel = productsViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_product_item)

        //val view: View = View.inflate(this, R.layout.seller_product_item, null)
        //initializeFields(view)

//        val productsList: MutableLiveData<List<Product>> = productsViewModel.getAllProducts()
//        productsList.observe(this, {
//            println("debug: product " + it)
//        })

    }

//    private fun initializeFields(view: View) {
//        titleTV = view.findViewById(R.id.productTitle)
//        priceTV = view.findViewById(R.id.productPrice)
//        descriptiontv = view.findViewById(R.id.productDesc)
//        imgIV = view.findViewById(R.id.productImagIv)
//        getGridData()
//    }
//
//    private fun getGridData(){
//        productDatabase = productsViewModel()
//        myArrayList = productDatabase.getAllProducts()
//        titles = ArrayList()
//        price = ArrayList()
//        ids = ArrayList()
//
//        myArrayList.observe(this) {
//            val myProduct : List<Product>? = it
//            if (myProduct != null) {
//                for(product in myProduct){
//                    titleTV.text = product.name
//                }
//            }
//
//        }
//
//    }



}