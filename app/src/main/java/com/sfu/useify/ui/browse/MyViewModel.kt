package com.sfu.useify.ui.browse

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModel: ViewModel() {
    val userImg = MutableLiveData<Bitmap>()
}
