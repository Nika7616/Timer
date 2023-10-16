package com.example.timer.presentation.Screens.Splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {
    private val _readyToMove = MutableLiveData<Boolean>()
    val readyToMove: LiveData<Boolean>
        get() = _readyToMove

    init {
        viewModelScope.launch {
            delay(1000)
            _readyToMove.value = true
        }
    }
}