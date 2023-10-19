package com.example.timer.presentation.Screens.Home

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timer.data.TimerRepositoryImpl
import com.example.timer.data.dataBaseSource.TrainingModel
import kotlinx.coroutines.launch

class HomeViewModel(var app: Application) : AndroidViewModel(app) {

    private var _trainingListLD = MutableLiveData<List<TrainingModel>>()
    val trainingListLD: LiveData<List<TrainingModel>>
        get() = _trainingListLD

    private var _currentTrainingLD = MutableLiveData<TrainingModel>()
    val currentTrainingLD: LiveData<TrainingModel>
        get() = _currentTrainingLD

    private val _currentTimeLD = MutableLiveData<Long>()
    val currentTimeLD: LiveData<Long>
        get() = _currentTimeLD

    private val _currentTypeLD = MutableLiveData<String>()
    val currentTypeLD: LiveData<String>
        get() = _currentTypeLD

    private val _sets = MutableLiveData<Int>()
    val sets: LiveData<Int>
        get() = _sets

    var currentTimeIteration = 1

    lateinit var timer: CountDownTimer

    val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState


    private var _progressMax = MutableLiveData<Int>()
    val progressMaxLD: LiveData<Int>
        get() = _progressMax

    private val _visible = MutableLiveData<HomeViewState>()
    val visible: LiveData<HomeViewState>
        get() = _visible

    val repository = TimerRepositoryImpl(app)

    init {
        _viewState.value = HomeViewState.enabled
        getList()
        _visible.value = HomeViewState.notVisibleStop
    }

    fun getList() {
        viewModelScope.launch {
            _viewState.postValue(HomeViewState.disabled)
            _trainingListLD = repository.getTrainingList()
            _viewState.postValue(HomeViewState.enabled)


        }
    }

    fun setCurrentTraining(currentTraining: TrainingModel) {
        try {
            timer.cancel()
        } catch (e: Exception) {

        }
        _visible.value = HomeViewState.notVisibleStop
        _currentTrainingLD.value = currentTraining
        _currentTimeLD.value = currentTraining.preparation
        _currentTypeLD.value = "подготовка"
        currentTimeIteration = 1
        _sets.value = currentTraining.sets
        _progressMax.value = (_currentTrainingLD.value?.preparation?.div(1000))?.toInt()

    }

    fun deleteItem(item: TrainingModel) {
        viewModelScope.launch {
            _viewState.postValue(HomeViewState.disabled)
            repository.deleteItem(item)
            _viewState.postValue(HomeViewState.enabled)
        }
    }
}