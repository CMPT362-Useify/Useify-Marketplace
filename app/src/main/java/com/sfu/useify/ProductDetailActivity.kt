package com.sfu.useify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setTitle(R.string.product_details_title)
    }
}