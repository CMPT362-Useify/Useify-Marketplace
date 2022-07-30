package com.sfu.useify.resetPages

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sfu.useify.R

class Password : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.password)
    }

    fun goToMain(view: View){
        val intent = Intent(this, GeneralSettings::class.java)
        startActivity(intent)
    }
}