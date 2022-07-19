package com.sfu.useify

import android.content.Intent

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sfu.useify.ui.signup.SignupActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onSearchClicked(view: View) {
        val intent = Intent(this, SearchCategoriesActivity::class.java)
        startActivity(intent)
    }

    fun onDetailsClicked(view: View) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }

    fun onSignUpClicked(view : View){
        val newScreen : Intent = Intent(this, SignupActivity::class.java)
        startActivity(newScreen)
    }
}