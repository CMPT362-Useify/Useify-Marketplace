package com.sfu.useify

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class SearchCategoriesActivity: AppCompatActivity() {
    private lateinit var CATEGORIES: Array<String>
    private lateinit var CATEGORIES_ICONS: TypedArray
    private lateinit var categoriesListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_categories)
        setTitle(R.string.search_categories_title)

        // Setup back button in Action Bar
        // https://www.geeksforgeeks.org/how-to-add-and-customize-back-button-of-action-bar-in-android/
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup categories list view
        CATEGORIES = resources.getStringArray(R.array.categories)
        CATEGORIES_ICONS = resources.obtainTypedArray(R.array.icons_categories)
        categoriesListView = findViewById(R.id.listview_categories)
        val categoryAdapter = CategoryAdapter(this, CATEGORIES, CATEGORIES_ICONS)
        categoriesListView.adapter = categoryAdapter

        // Show results for the appropriate category
        categoriesListView.setOnItemClickListener() { adapterView, view, i, l ->
            onCategoryClicked(i)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Show results given the selected category
    private fun onCategoryClicked(pos: Int) {
        val categoryIntent = Intent(this, ResultsActivity::class.java)

        val bundle = Bundle()
        bundle.putInt(resources.getString(R.string.key_category_id), pos)

        categoryIntent.putExtras(bundle)
        startActivity(categoryIntent)
    }
}