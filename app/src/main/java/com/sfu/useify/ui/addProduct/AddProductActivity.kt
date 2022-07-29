package com.sfu.useify.ui.addProduct

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.sfu.useify.MainActivity
import com.sfu.useify.R
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product

class AddProductActivity : AppCompatActivity() {

    // editText fields
    private lateinit var titleET: EditText
    private lateinit var priceET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var pickUpLoctionET: EditText
    private lateinit var restrictionET: EditText

    // TODO: get images and seller ID
    private var imgUrl = "img.jpg"
    private var sellerID = "0"

    val productsViewModel = productsViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)


        //get products input fields
        titleET = findViewById(R.id.productTitleEt)
        priceET = findViewById(R.id.productPriceEt)
        descriptionET = findViewById(R.id.productDescEt)
        pickUpLoctionET = findViewById(R.id.productLocationEt)
        restrictionET = findViewById(R.id.productRestrictionsEt)

        val productsList: MutableLiveData<List<Product>> = productsViewModel.getAllProducts()
        productsList.observe(this, {
            println("debug: product " + it)
        })


    }

    fun onAddNewProductClicked(view: View) {

        // TODO: add some validation(empty,data type) before insert

        val newProduct = Product(
            titleET.text.toString(),
            priceET.text.toString().toDouble(),
            imgUrl,
            descriptionET.text.toString(),
            sellerID
        )
        productsViewModel.addProduct(newProduct)

        Toast.makeText(this, "new product added", Toast.LENGTH_LONG).show()

        // back to main page
        val intent =  Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)

    }
}

