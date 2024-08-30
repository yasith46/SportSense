package com.google.mediapipe.examples.poselandmarker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter(private var items: List<LeaderboardItem>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false)
        return LeaderboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val item = items[position]
        holder.fieldNameTextView.text = item.fieldName
        holder.valueTextView.text = item.value.toString()
    }

    override fun getItemCount(): Int = items.size

    // Method to update data and refresh RecyclerView
    fun updateData(newItems: List<LeaderboardItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fieldNameTextView: TextView = itemView.findViewById(R.id.fieldNameTextView)
        val valueTextView: TextView = itemView.findViewById(R.id.valueTextView)
    }
}
