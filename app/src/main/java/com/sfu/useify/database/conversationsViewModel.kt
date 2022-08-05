package com.sfu.useify.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.sfu.useify.models.Conversation
import com.sfu.useify.models.Product

class conversationsViewModel {

    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("Conversations")

    private var conversation:MutableLiveData<Conversation> = MutableLiveData()

    fun getOrAddNewConversation(productId: String, userId: String, sellerID: String): MutableLiveData<Conversation> {
        databaseReference.orderByChild("productID").equalTo(productId).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val ret_conversation = snapshot.children.mapNotNull { it.getValue(Conversation::class.java) }.toList()[0]
                    conversation.postValue(ret_conversation)
                } else{
                    val newConversationRef = databaseReference.push()
                    val senderIDs: List<String> = listOf(sellerID, userId)
                    val newConversation = Conversation(newConversationRef.key.toString(), productId,  senderIDs)
                    newConversationRef.setValue(newConversation).addOnSuccessListener {
                        Log.i("firebase", "Successfully added new conversation")
                        conversation.postValue(newConversation)
                    }.addOnFailureListener {
                        Log.i("firebase","Error adding new conversation", it)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        return conversation
        
    }
}