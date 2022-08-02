package com.sfu.useify.ui.browse

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.sfu.useify.R
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.models.Product
import com.sfu.useify.ui.productDetails.ProductDetailActivity

class BrowseActivity: AppCompatActivity() {

    companion object {
        var CURRENT_USER_KEY = "current user"
    }

    private lateinit var gridView: GridView
    private lateinit var titles: MutableList<String>
    private lateinit var price: MutableList<Float>
    private lateinit var ids: MutableList<String>
    private lateinit var images: IntArray
    private lateinit var productDatabase: productsViewModel
    private lateinit var myArrayList: MutableLiveData<List<Product>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        //Create an action bar and make it go back to home page.
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        getGridData()
        setGridView()
        gridViewOnClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    /**
     * Description: gets names and images from database
     * Post-condition: changes names and images variables
     */
    private fun getGridData(){
        productDatabase = productsViewModel()
        myArrayList = productDatabase.getAllProducts()
        titles = ArrayList()
        price = ArrayList()
        ids = ArrayList()

        myArrayList.observe(this) {
            val myProduct : List<Product>? = it
            if (myProduct != null) {
                for(product in myProduct){
                    ids.add(product.productID)
                    titles.add(product.name)
                    price.add(product.price.toFloat())
                }
                setGridView()
            }

        }

        images = intArrayOf(
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer,
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer,
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer,
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer,
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer,
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer,
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer,
            R.drawable.gaming_computer, R.drawable.gaming_computer, R.drawable.gaming_computer
        )
    }

    /**
     * Description: sets gridview elements with name and photo of each profile
     * Post-condition: gridview is set to xml element and adapter is set
     */
    private fun setGridView() {
        gridView = findViewById(R.id.browseGridView)
        val profileAdapter = ProfileAdapter(this, titles, price, images)
        gridView.adapter = profileAdapter
    }

    /**
     * Description: opens main menu on grid item selected
     * Post-condition: current user changed to selected item
     */
    private fun gridViewOnClickListener() {
        gridView!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val intent = Intent(applicationContext, ProductDetailActivity::class.java)
                intent.putExtra("productIdKey", ids[position])
                startActivity(intent)
            }
    }

}






