package com.sfu.useify.resetPages

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sfu.useify.R

class Username : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.username)
    }

    fun goToMain(view: View){
        val intent = Intent(this, GeneralSettings::class.java)
        startActivity(intent)
    }
}