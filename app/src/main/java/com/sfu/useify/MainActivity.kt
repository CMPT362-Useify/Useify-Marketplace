package com.sfu.useify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sfu.useify.ui.signup.SignupActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onSignUpClicked(view : View){
        val newScreen : Intent = Intent(this, SignupActivity::class.java)
        startActivity(newScreen)
    }
}