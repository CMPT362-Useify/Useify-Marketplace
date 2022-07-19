package com.sfu.useify.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Product (
    var name:String = "",
    var price: Double = 0.0,
    var image: String = "",
    var description: String = "",
    var sellerID: String = ""
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "name" to name,
            "price" to price,
            "image" to image,
            "description" to description,
            "sellerID" to sellerID)
    }
}