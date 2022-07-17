package com.sfu.useify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SearchCategoriesActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_categories)
        setTitle(R.string.search_categories_title)
    }
}