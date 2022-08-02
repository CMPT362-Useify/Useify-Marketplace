package com.sfu.useify.resetPages

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sfu.useify.MainActivity
import com.sfu.useify.R

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

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

    fun onResetPassword(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}