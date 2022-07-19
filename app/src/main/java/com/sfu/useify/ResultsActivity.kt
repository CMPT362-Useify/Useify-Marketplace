package com.sfu.useify

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.search_results_title)
        setContentView(R.layout.activity_results)


        // Setup back button in Action Bar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras
        val categoryID: Int? = bundle?.getInt(resources.getString(R.string.key_category_id))

        if (categoryID != null){
            val category: String = resources.getStringArray(R.array.categories)[categoryID]
            println("Debug: '$category' category selected")
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
}