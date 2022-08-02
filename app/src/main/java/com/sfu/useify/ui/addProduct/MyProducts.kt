package com.sfu.useify.ui.addProduct

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sfu.useify.R

class MyProducts: AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.seller_product_item)

        }

        fun changeTheme(view: View){
            val nightModeFlags = resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK
            if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

        }
}