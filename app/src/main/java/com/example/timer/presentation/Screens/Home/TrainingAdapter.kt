package com.example.timer.presentation.Screens.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.data.dataBaseSource.TrainingModel

class TrainingAdapter: RecyclerView.Adapter<TrainingViewHolder>() {

    var onItemClickListener: ((TrainingModel) -> Unit)? = null
    var onItemLongClickListener: ((TrainingModel) -> Unit)? = null


    var trainingList = listOf<TrainingModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return TrainingViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TrainingViewHolder, position: Int) {
        val trainingItem = trainingList[position]

        viewHolder.tvName.text = trainingItem.name
        viewHolder.tvID.text = trainingItem.id.toString()

        viewHolder.view.setOnClickListener {
            onItemClickListener?.invoke(trainingItem)
        }

        viewHolder.view.setOnLongClickListener {
            onItemLongClickListener?.invoke(trainingItem)
            true
        }
    }

    override fun onViewRecycled(viewHolder: TrainingViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }



}