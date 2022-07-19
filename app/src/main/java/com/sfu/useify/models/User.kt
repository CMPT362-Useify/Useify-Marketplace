package com.sfu.useify.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
    var name: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
    var email: String? = "",
    var phoneNumber: String? = "",
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "name" to name,
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phoneNumber" to phoneNumber
        )
    }
}