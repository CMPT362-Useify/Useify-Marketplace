package com.sfu.useify.ui.signup

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignUpViewModel: ViewModel() {
    val userImg = MutableLiveData<Bitmap>()
}
