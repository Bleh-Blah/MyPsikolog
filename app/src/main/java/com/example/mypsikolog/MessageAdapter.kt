package com.example.mypsikolog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // sets values of item to determine view type
    val ITEM_RECIEVE = 0
    val ITEM_SENT = 1

    // to create a layout of messages
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.other_user_message_layout, parent, false)
            return ReceiveViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.user_message_layout, parent, false)
            return SentViewHolder(view)
        }
    }

    // to input data into the messages
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
            holder.sentTime.text = currentMessage.timeSent?.substring(0,8)
        } else {
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
            holder.receiveTime.text = currentMessage.timeSent?.substring(0,8)
        }
    }

    // to get the view type for checking if its sent or received
    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECIEVE
        }
    }

    // gets item size
    override fun getItemCount(): Int {
        return messageList.size
    }

    // a view holder for messages sent by user
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.tvMessage_user_message_layout)
        val sentTime = itemView.findViewById<TextView>(R.id.tvTime_user_message_layout)
    }

    // a view holder for messages sent by the other user
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.tvMessage_other_user_message_layout)
        val receiveTime = itemView.findViewById<TextView>(R.id.tvTime_other_user_message_layout)
    }
}