package com.example.timer.domain

import androidx.lifecycle.MutableLiveData
import com.example.timer.data.dataBaseSource.TrainingModel

interface TrainingRepository {
    suspend fun getTrainingList(): MutableLiveData<List<TrainingModel>>

    suspend fun deleteItem(item: TrainingModel)

    suspend fun editItem(newElement: TrainingModel)
}