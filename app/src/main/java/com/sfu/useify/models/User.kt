package com.sfu.useify.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
    var userID:String? ="",
    var username: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
    var avatar:String? = "",
    var email: String? = "",
    var phoneNumber: String? = "",
    var address: String? = ""
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "userID" to userID,
            "username" to username,
            "firstName" to firstName,
            "lastName" to lastName,
            "avatar" to avatar,
            "email" to email,
            "phoneNumber" to phoneNumber,
            "address" to address
        )
    }
}