package com.example.timer.presentation.Screens.AddTraining

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.timer.data.dataBaseSource.TrainingModel
import com.example.timer.presentation.Screens.Home.HomeViewState
import com.example.timer.presentation.Screens.Timer.TrainingViewModel
import kotlinx.coroutines.launch

class AddTrainingViewModel(var app: Application) : AndroidViewModel(app) {
    private lateinit var viewModel: TrainingViewModel


    fun addItem(model: TrainingModel) {
        viewModelScope.launch {
            viewModel._viewState.postValue(HomeViewState.disabled)
            viewModel.repository.addItem(model)
            viewModel._viewState.postValue(HomeViewState.enabled)

        }

    }


    fun timeMinSecToMillisec (min: Int, sec: Int): Long {

        return ((min*60 + sec) * 1000).toLong()
    }

}