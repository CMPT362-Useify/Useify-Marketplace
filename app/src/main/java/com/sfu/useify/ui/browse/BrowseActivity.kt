package com.sfu.useify.ui.browse

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.sfu.useify.R
import com.sfu.useify.ui.productDetails.ProductDetailActivity
import com.sfu.useify.ui.signup.SignupActivity

class BrowseActivity: AppCompatActivity() {

    companion object {
        var CURRENT_USER_KEY = "current user"
    }

    private lateinit var gridView: GridView
    private lateinit var titles: Array<String>
    private lateinit var price: FloatArray
    private lateinit var images: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)


        getGridData()
        setGridView()
        gridViewOnClickListener()
    }

    /**
     * Description: gets names and images from database
     * Post-condition: changes names and images variables
     */
    fun getGridData(){
        titles = arrayOf(
            "Computer", "Computer", "Computer", "Computer",
            "Computer", "Computer", "Computer", "Computer",
            "Computer", "Computer", "Computer", "Computer",
            "Computer", "Computer", "Computer", "Computer",
            "Computer", "Computer", "Computer", "Computer",
            "Computer", "Computer", "Computer", "Computer"
        )
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
        price = floatArrayOf(
            123.12F, 123.12F, 123.12F,
            123.12F, 123.12F, 123.12F,
            123.12F, 123.12F, 123.12F,
            123.12F, 123.12F, 123.12F,
            123.12F, 123.12F, 123.12F,
            123.12F, 123.12F, 123.12F,
            123.12F, 123.12F, 123.12F,
            123.12F, 123.12F, 123.12F
        )
    }

    /**
     * Description: sets gridview elements with name and photo of each profile
     * Post-condition: gridview is set to xml element and adapter is set
     */
    private fun setGridView() {
        gridView = findViewById(R.id.browseGridView)
        val profileAdapter = ProfileAdapter(this, titles, price, images)
        gridView.setAdapter(profileAdapter)
    }

    /**
     * Description: opens main menu on grid item selected
     * Post-condition: current user changed to selected item
     */
    private fun gridViewOnClickListener() {
        gridView!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val bundle = Bundle()
                bundle.putString(CURRENT_USER_KEY, titles[position])
                val intent = Intent(applicationContext, ProductDetailActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
    }

}






