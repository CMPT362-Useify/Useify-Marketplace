package com.sfu.useify.resetPages

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sfu.useify.MainActivity
import com.sfu.useify.R

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

    }

    fun onResetPassword(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}