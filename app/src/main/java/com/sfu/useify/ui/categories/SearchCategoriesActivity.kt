package com.sfu.useify.ui.categories

import android.content.Intent
import android.content.res.Configuration
import android.content.res.TypedArray
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sfu.useify.R
import com.sfu.useify.ui.results.ResultsActivity

class SearchCategoriesActivity: AppCompatActivity() {
    private lateinit var CATEGORIES: Array<String>
    private lateinit var CATEGORIES_ICONS: TypedArray
    private lateinit var categoriesListView: ListView
    private lateinit var searchView: SearchView

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

        // Setup SearchView
        searchView = findViewById(R.id.searchView_search_categories)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryString: String?): Boolean {
                println("Debug: Query received = '$queryString'")

                val searchIntent = Intent(this@SearchCategoriesActivity, ResultsActivity::class.java)

                val bundle = Bundle()
                bundle.putString(resources.getString(R.string.key_search_query), queryString)

                searchIntent.putExtras(bundle)
                startActivity(searchIntent)

                // true if the query has been handled by the listener, false to let the
                // SearchView perform the default action.
                return true
            }

            override fun onQueryTextChange(queryString: String?): Boolean {
                // false if the SearchView should perform the default action of showing any
                // suggestions if available, true if the action was handled by the listener.
                return true
            }
        })
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

    fun changeTheme(view: View){
        val nightModeFlags = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

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