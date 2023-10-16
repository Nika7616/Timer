package com.example.timer.presentation.Screens.Home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R

class TrainingViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tv_name)
    val tvID = view.findViewById<TextView>(R.id.tv_ID)
}