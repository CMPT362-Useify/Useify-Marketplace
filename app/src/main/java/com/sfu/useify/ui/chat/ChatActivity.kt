package com.sfu.useify.ui.chat

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sfu.useify.R
import com.sfu.useify.Util
import com.sfu.useify.database.conversationsViewModel
import com.sfu.useify.database.productsViewModel
import com.sfu.useify.database.usersViewModel
import com.sfu.useify.models.Conversation
import com.sfu.useify.models.Message
import java.util.*

class ChatActivity: AppCompatActivity() {
    private lateinit var usernameTextView: TextView
    private lateinit var chatInputEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersViewModel: usersViewModel
    private lateinit var productsViewModel: productsViewModel
    private lateinit var conversationsViewModel: conversationsViewModel
    private lateinit var chatAdapter: ChatAdapter

    // Used to avoid checking for conversation after every new message sent
    private var newConversation: Boolean = false
    private var conversationExistenceCheck: Boolean = false
    private var mUserID: String? = null
    private var otherUserID: String? = null
    private var productID: String? = null
    private var conversationID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setupView()

        // Get ViewModels
        usersViewModel = usersViewModel()
        productsViewModel = productsViewModel()
        conversationsViewModel = conversationsViewModel()

        // Get bundle contents
        val bundle = intent.extras
        getBundle(bundle)
        println("Debug-chat: my user id = $mUserID, seller's id = $otherUserID, product id = $productID")

        // Set username of conversation partner
        usersViewModel.getUserByID(otherUserID!!).observe(this, Observer {
            usernameTextView.text = it.username
        })

        setupObserverAndAdapter()
    }

    private fun setupView() {
        // Hide Action Bar
        supportActionBar?.hide()

        // Get views
        usernameTextView = findViewById<TextView>(R.id.textview_chat_user)
        chatInputEditText = findViewById<EditText>(R.id.edittext_chat_input)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_chat_messages)

        // Specify keyboard action (send message)
        chatInputEditText.setOnEditorActionListener { textView, actionID, keyEvent ->
            when (actionID){
                EditorInfo.IME_ACTION_SEND -> sendMessage()
            }
            return@setOnEditorActionListener false
        }
    }


    // Get bundle contents and set the respective variables
    private fun getBundle(bundle: Bundle?) {
        mUserID = Util.getUserID()
        otherUserID = bundle?.getString(resources.getString(R.string.key_chat_other_user_id))
        productID = bundle?.getString(resources.getString(R.string.key_chat_product_id))

        if (mUserID == null || otherUserID == null || productID == null){
            // TODO: Handle error
            finish()
        }
    }


    // References:
    // https://stackoverflow.com/questions/46168245/recyclerview-reverse-order
    private fun setupObserverAndAdapter(){
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
//        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        conversationsViewModel.
        getOrAddNewConversation(productID!!, mUserID!!, otherUserID!!).observe(this, Observer { conversation ->
            // TODO: test new conversation by new user when bug fixed
            println("Debug-chat: Database returns a conversation between userID = ${conversation.senderIDs[0]} and userID = ${conversation.senderIDs[1]}  ... product id = ${conversation.productID}}")
            conversationID = conversation.conversationID
            usersViewModel.getAllConversations(mUserID!!)
                .observe(this, Observer { userConversations ->
                    println("Debug-chat: userConversations = $userConversations")
                    if (!(userConversations.contains(conversation.conversationID))){
                        newConversation = true
                    }
                })
            // Create chat history
            conversationsViewModel.
            getAllMessagesByConversationID(conversation.conversationID).observe(this, Observer { messages ->
                val sortedMessageList = sortMessages(messages!!)
                chatAdapter = ChatAdapter(this, sortedMessageList, mUserID!!)
                recyclerView.adapter = chatAdapter
            })
        })
    }


    // Sort conversation message list by time
    private fun sortMessages(messages: List<Message>): List<Message> {
        messages.toMutableList()
        for (i in 0 until (messages.size-1)){
            var max = i
            for (j in i+1 until messages.size){
                if (messages[j].sentAt > messages[max].sentAt)
                    max = j
            }
            if (max != i){
                Collections.swap(messages, i, max)
            }
        }
        return messages.toList()
    }


    // Send message to user
    private fun sendMessage() {
        // Check to make sure message isn't empty
        val messageContent = chatInputEditText.text.toString()  // Get message contents from editText
        if (messageContent == "")
            return

        // Check if the conversation exists in the users list of conversations
        // Add it if it doesn't
        if (!conversationExistenceCheck) {
            if (newConversation){
                usersViewModel.addConversation(mUserID!!, conversationID!!)
                usersViewModel.addConversation(otherUserID!!, conversationID!!)
                newConversation = false
            }
            conversationExistenceCheck = true
        }

        // Reset to blank
        chatInputEditText.setText("")

        // Create new message object
        val message = Message(messageContent, mUserID!!)

        // Add message to conversation livedata
        conversationsViewModel.addMessageByConversationID(message, conversationID!!)
    }

    fun onSendMessageClicked(view: View){
        sendMessage()
    }

    fun onBackClicked(view: View){
        finish()
    }
}