package com.sfu.useify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ProfileAdapter(var context: Context, var title: Array<String>, var price: FloatArray,
                     var image: IntArray) : BaseAdapter() {
    var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return title.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
        var view = view
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (view == null) {
            view = layoutInflater!!.inflate(R.layout.login_grid_item, null)
        }
        val imageView = view?.findViewById<ImageView>(R.id.grid_image)
        val priceView = view?.findViewById<TextView>(R.id.item_price)
        val titleView = view?.findViewById<TextView>(R.id.item_title)
        imageView?.setImageResource(image[position])
        priceView?.text = "$" + price[position]
        titleView?.text = title[position]
        return view
    }
}
