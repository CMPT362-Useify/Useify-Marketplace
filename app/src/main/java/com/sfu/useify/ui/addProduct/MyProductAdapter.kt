package com.sfu.useify.ui.addProduct

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.sfu.useify.R
import com.sfu.useify.Util
import com.sfu.useify.ui.productDetails.ProductDetailActivity
import com.squareup.picasso.Picasso
import java.util.concurrent.Executors

class MyProductAdapter(
    val context: Context,
    var idsList: MutableList<String>,
    var titleList: MutableList<String>,
    var pricesList: MutableList<Float>,
    var descList: MutableList<String>,
    var imagesList: MutableList<String>
) : BaseAdapter() {

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return titleList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.seller_product_item, null)

        val titleTV = view.findViewById(R.id.productTitle) as TextView
        val priceTV = view.findViewById(R.id.productPrice) as TextView
        val descriptionTV = view.findViewById(R.id.productDesc) as TextView
        val imgIV = view.findViewById(R.id.productImagIv) as ImageView

        //clickable icons
        val viewItemBtn = view.findViewById(R.id.viewItem) as ImageButton
        val editItemBtn = view.findViewById(R.id.editItem) as ImageButton

        var productID = idsList[position]
        titleTV?.text = titleList[position]
        priceTV?.text = "$" + pricesList[position]
        descriptionTV?.text = descList[position]

        //set image
        var imgUrl = imagesList[position]
        if (imgUrl != "") {
            Picasso.get().load(imgUrl).resize(250,0).centerCrop().placeholder(R.drawable.ic_baseline_image_500).into(imgIV)
            //Util.loadImgInView(imgUrl, imgIV)
        }

        viewItemBtn.setOnClickListener {
            val intent = Intent(view.context, ProductDetailActivity::class.java)
            intent.putExtra("productIdKey", productID)
            view.context.startActivity(intent)
        }

        editItemBtn.setOnClickListener {
            val intent = Intent(view.context, AddEditProductActivity::class.java)
            intent.putExtra("productIdKey", productID)
            view.context.startActivity(intent)
        }

        return view
    }

}