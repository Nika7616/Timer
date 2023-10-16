package com.example.timer.presentation.Screens.Home

import androidx.recyclerview.widget.DiffUtil
import com.example.timer.data.dataBaseSource.TrainingModel

class MyDiffUtill: DiffUtil.ItemCallback<TrainingModel>() {

    override fun areItemsTheSame(oldItem: TrainingModel, newItem: TrainingModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrainingModel, newItem: TrainingModel): Boolean {
        return oldItem == newItem
    }
}