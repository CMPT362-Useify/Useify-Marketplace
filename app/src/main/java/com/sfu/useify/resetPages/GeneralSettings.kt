package com.sfu.useify.resetPages

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sfu.useify.MainActivity
import com.sfu.useify.R
import com.sfu.useify.ui.authentication.login.LoginActivity

class GeneralSettings : AppCompatActivity() {

    private lateinit var logout_btn :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_general_settings)

        logout_btn = findViewById(R.id.logout)

        logout_btn.setOnClickListener( {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        })


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

    fun changeUsername(view : View){
        val intent = Intent(this, Username::class.java)
        startActivity(intent)
    }

    fun changePassword(view: View){
        val intent = Intent(this, Password::class.java)
        startActivity(intent)
    }

    fun goToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}