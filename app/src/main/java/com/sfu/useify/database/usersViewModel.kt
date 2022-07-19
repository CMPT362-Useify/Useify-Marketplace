package com.sfu.useify.database

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sfu.useify.models.User

class usersViewModel: ViewModel() {
    private var users: MutableLiveData<List<User>> = MutableLiveData()
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("Users")


    //user_id will be provided when the authentication feature is completed
    //for now, we can use some random string (e.g. "123", "1", "abc", etc.) as a user id
    //user_id must be unique, otherwise, it will override existing user in database
    fun addUser(user: User, user_id: String) {
        databaseReference.child(user_id).setValue(user).addOnSuccessListener {
            Log.i("firebase", "Success fully added user")
        }.addOnFailureListener {
            Log.i("firebase","Error adding user", it)
        }
    }

    fun getAllUsers(): MutableLiveData<List<User>>{
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val usersList = snapshot.children.mapNotNull { it.getValue(User::class.java) }.toList()
                    users.postValue(usersList)
                } else{
                    users.postValue(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return users
    }

    //key: id of the user in database
    fun deleteUser(key:String){
        databaseReference.child(key).removeValue()
    }

    //key: id of the user in database
    fun updateUser(key: String, user:User){
        databaseReference.child(key).setValue(user)
    }

}