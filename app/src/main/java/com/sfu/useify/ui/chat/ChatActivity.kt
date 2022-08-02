package com.sfu.useify.ui.chat

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sfu.useify.R

class ChatActivity: AppCompatActivity() {
    private lateinit var usernameTextView: TextView
    private lateinit var chatInputEditText: EditText
    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()

        // Get bundle
        val bundle = intent.extras
        usernameTextView = findViewById<TextView>(R.id.textview_chat_user)
        chatInputEditText = findViewById<EditText>(R.id.edittext_chat_input)

        userID = bundle?.getString(resources.getString(R.string.key_chat_seller_id))
        if (userID == null)
            // TODO: Throw error
        usernameTextView.text = userID

        // Specify keyboard action (send message)
        chatInputEditText.setOnEditorActionListener { textView, actionID, keyEvent ->
            when (actionID){
                EditorInfo.IME_ACTION_SEND -> sendMessage()
            }
            return@setOnEditorActionListener false
        }
    }


    // Send message to user
    private fun sendMessage() {
        // TODO: Send message
        val message = chatInputEditText.text
    }

    fun onSendMessageClicked(view: View){
        sendMessage()
    }

    fun onBackClicked(view: View){
        finish()
    }
}