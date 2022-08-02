package com.sfu.useify.ui.chat

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.sfu.useify.R

class ConversationsAdapter: RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder>() {
    class ConversationViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val profilePictureImageView: ImageView
        val usernameTextView: TextView
        init {
            profilePictureImageView = view.findViewById(R.id.imageview_conversation_profile_pic)
            usernameTextView = view.findViewById(R.id.textview_conversation_username)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listview_conversation, parent, false)

        return ConversationViewHolder(view)
    }

    // Replace the contents of the view with user names and icon
    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
//        holder.profilePictureImageView.setImageDrawable()
        holder.usernameTextView.text = "test" // dataSet[position]
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
//        return dataSet.size
        return 0
    }
}