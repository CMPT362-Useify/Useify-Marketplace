package com.sfu.useify.ui.signup

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.sfu.useify.*
import com.sfu.useify.database.usersViewModel
import com.sfu.useify.models.User
import com.sfu.useify.ui.browse.BrowseActivity
import java.io.File

class SignupActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var cameraResult : ActivityResultLauncher<Intent>
    private lateinit var myViewModel : SignUpViewModel
    private lateinit var imgUri: Uri
    private val imgFileName = "signUpImage.jpg"

    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var emailAddress : EditText
    private lateinit var phoneNumber : EditText

    private lateinit var userViewModel: usersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Util.checkPermissions(this)

        userViewModel = usersViewModel()


        firstName = findViewById(R.id.signup_first_name)
        lastName = findViewById(R.id.signup_last_name)
        emailAddress = findViewById(R.id.signup_first_name)
        phoneNumber = findViewById(R.id.signup_first_name)

        profileImage = findViewById(R.id.signup_image)

        loadSavedProfilePicture()
        checkForNewProfilePictureAndSet()
        captureAndSetProfilePicture()

    }

    fun loadSavedProfilePicture(){
        // checks file location for image of imgFileName
        val imgFile = File(getExternalFilesDir(null), imgFileName)
        imgUri = FileProvider.getUriForFile(this, "com.sfu.useify",
            imgFile)


        if (imgFile.exists()){
            // sets Stored Profile Picture
            val bitmap = Util.getBitmap(this, imgUri)
            profileImage.setImageBitmap(bitmap)
        }
    }

    fun checkForNewProfilePictureAndSet(){
        // check if the image has changed
        myViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        myViewModel.userImg.observe(this){
            // sets new profilePicture
            val bitmap = Util.getBitmap(this, imgUri)
            profileImage.setImageBitmap(bitmap)
        }
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

    fun captureAndSetProfilePicture(){
        // sets profile picture onto ImageView
        cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                it: ActivityResult ->
            if (it.resultCode == Activity.RESULT_OK){
                val bitmap = Util.getBitmap(this, imgUri)
                myViewModel.userImg.value = bitmap
            }
        }
    }

    fun onChangeProfilePhotoClicked(view : View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
        cameraResult.launch(intent)
    }

    fun onSignupSaveClicked(view : View) {
        val name : String = firstName.text.toString() + (lastName.text.toString())
        val first = firstName.text.toString()
        val last = lastName.text.toString()
        val email = emailAddress.text.toString()
        val phone = phoneNumber.text.toString()


        val newUser = User(name, first, last, email, phone)
        userViewModel.addUser(newUser, "1")


        val newScreen : Intent = Intent(this, BrowseActivity::class.java)
        startActivity(newScreen)
    }

    fun onSignupCancelClicked(view : View) {
        val newScreen : Intent = Intent(this, MainActivity::class.java)
        startActivity(newScreen)
    }
}