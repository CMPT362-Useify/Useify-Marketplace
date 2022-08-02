package com.sfu.useify.ui.browse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.sfu.useify.R
import com.squareup.picasso.Picasso
import java.util.*

class ProfileAdapter(
    var context: Context, var title: MutableList<String>, var price: MutableList<Float>,
    var image: MutableList<String>, var date: MutableList<String>
) : BaseAdapter() {
    var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return title.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
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

        val dateView = view?.findViewById<TextView>(R.id.item_date)
        Picasso.get().load(image[position]).into(imageView)
        priceView?.text = "$" + price[position]
        titleView?.text = title[position]
        dateView?.text = date[position]
        return view
    }
}
