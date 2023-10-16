package com.example.timer.presentation.Screens.EditTraining

import android.app.Application
import androidx.lifecycle.*
import com.example.timer.data.TimerRepositoryImpl
import com.example.timer.data.dataBaseSource.TrainingModel
import com.example.timer.presentation.Screens.Home.HomeViewState
import com.example.timer.presentation.Screens.Timer.TrainingViewModel
import kotlinx.coroutines.launch

class EditTrainingViewModel(var app: Application) : AndroidViewModel(app) {
    val repository = TimerRepositoryImpl(app)

    private var _currentTrainingforEditLD = MutableLiveData<TrainingModel>()
    val currentTrainingforEditLD: LiveData<TrainingModel>
        get() = _currentTrainingforEditLD

    val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState



    fun editItem(item: TrainingModel) {
        viewModelScope.launch {
            _viewState.postValue(HomeViewState.disabled)
            repository.editItem(item)
            _viewState.postValue(HomeViewState.enabled)
        }
    }

    fun timeMinSecToMillisec (min: Int, sec: Int): Long {

        return ((min*60 + sec) * 1000).toLong()
    }



    fun millToMinSek(mill: Long): TrainingViewModel.Companion.TimeSecMin {
        var min : Long = mill / 1000 / 60
        var sec = (mill - min * 60 *1000) / 1000

        val time = TrainingViewModel.Companion.TimeSecMin(min = min, sec = sec)

        return time
    }


    fun setCurrentTrainingForEdit (currentTrainingEdit: TrainingModel) {
        _currentTrainingforEditLD.value = currentTrainingEdit
    }

}