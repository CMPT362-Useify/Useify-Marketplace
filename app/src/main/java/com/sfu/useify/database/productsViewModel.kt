package com.sfu.useify.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sfu.useify.models.Product

class productsViewModel: ViewModel() {
    private var products: MutableLiveData<List<Product>> = MutableLiveData()
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("Products")

    fun addProduct(product: Product) {
        databaseReference.push().setValue(product).addOnSuccessListener {
            Log.i("firebase", "Success fully added product")
        }.addOnFailureListener {
            Log.i("firebase","Error adding product", it)
        }
    }

    fun getAllProducts(): MutableLiveData<List<Product>>{
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val productsList = snapshot.children.mapNotNull { it.getValue(Product::class.java) }.toList()
                    products.postValue(productsList)
                } else{
                    products.postValue(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return products
    }

    //key: id of the product in database
    fun deleteProduct(key:String){
        databaseReference.child(key).removeValue()
    }

    //key: id of the product in database
    fun updateProduct(key: String, product: Product){
        databaseReference.child(key).setValue(product)
    }

}