package com.example.timer.presentation.Screens.Timer

import android.app.Application
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.timer.R
import com.example.timer.data.TimerRepositoryImpl
import com.example.timer.data.dataBaseSource.TrainingModel
import com.example.timer.presentation.Screens.Home.HomeViewState
import kotlinx.coroutines.launch

class TrainingViewModel(var app: Application) : AndroidViewModel(app) {
    val repository = TimerRepositoryImpl(app)

    lateinit var sound: MediaPlayer


    private var _trainingListLD = MutableLiveData<List<TrainingModel>>()
    val trainingListLD: LiveData<List<TrainingModel>>
        get() = _trainingListLD


    private var _progressMax = MutableLiveData<Int>()
    val progressMaxLD: LiveData<Int>
        get() = _progressMax


    private var _currentTrainingLD = MutableLiveData<TrainingModel>()
    val currentTrainingLD: LiveData<TrainingModel>
        get() = _currentTrainingLD

    val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    private val _visible = MutableLiveData<HomeViewState>()
    val visible: LiveData<HomeViewState>
        get() = _visible

    private val _currentTimeLD = MutableLiveData<Long>()
    val currentTimeLD: LiveData<Long>
        get() = _currentTimeLD

    private val _currentTypeLD = MutableLiveData<String>()
    val currentTypeLD: LiveData<String>
        get() = _currentTypeLD

    private val _sets = MutableLiveData<Int>()
    val sets: LiveData<Int>
        get() = _sets


    lateinit var timer: CountDownTimer
    var isStoped = false

    var currentTimeIteration = 1


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


    fun startTime() {

        if (currentTimeIteration <= _currentTrainingLD.value!!.sets * 2 + 1 && isStoped == false) {
            sound = MediaPlayer.create(app, R.raw.call)

            if (currentTimeIteration == 1) {
                _currentTimeLD.value = _currentTrainingLD.value?.preparation
                _progressMax.value = (_currentTrainingLD.value?.preparation?.div(1000))?.toInt()
                _currentTypeLD.value = "подготовка"

            } else if (currentTimeIteration % 2 == 0) {
                _currentTimeLD.value = _currentTrainingLD.value?.training?.plus(1000)
                _progressMax.value = (_currentTrainingLD.value?.training?.div(1000))?.toInt()
                _currentTypeLD.value = "работа"
                sound.start()

            } else if (currentTimeIteration % 2 != 0) {
                _currentTimeLD.value = _currentTrainingLD.value?.rest?.plus(1000)
                _progressMax.value = (_currentTrainingLD.value?.rest?.div(1000))?.toInt()
                _currentTypeLD.value = "отдых"
                sound.start()

            }

        }


        _currentTimeLD.value?.let { starCountDownTimer(it) }
        isStoped = false

        _visible.value = HomeViewState.notVisibleStart

    }


    fun starCountDownTimer(time: Long) {
        timer = object : CountDownTimer(time, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTimeLD.value = millisUntilFinished
            }

            override fun onFinish() {

                if (currentTimeIteration != 1 && currentTimeIteration % 2 != 0) {
                    _sets.value = _sets.value?.minus(1)

                }


                if (currentTimeIteration < _currentTrainingLD.value!!.sets * 2 + 1) {

                    currentTimeIteration++
                    startTime()

                } else {
                    _currentTypeLD.value = "конец"
                }
            }
        }

        timer.start()
    }

    fun stopTimer() {
        _visible.value = HomeViewState.notVisibleStop
        timer.cancel()
        isStoped = true

    }


    fun millToMinSekSingleLine(mill: Long): String {
        var toSec = mill / 1000
        var min = (toSec / 60).toInt()
        val a = min * 60
        val sec = toSec - a
        var min1 = ""
        var sec1 = ""

        if (min < 10) {
            min1 = "0${min}"
        }
        if (min >= 10) {
            min1 = min.toString()
        }

        if (sec < 10) {
            sec1 = "0${sec}"
        }

        if (sec >= 10) {
            sec1 = sec.toString()
        }

        return "${min1}:${sec1}"
    }


    companion object{
        data class TimeSecMin(
            var min: Long,
            var sec: Long
        )
    }
}






