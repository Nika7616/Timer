package com.example.timer.presentation.Screens.Home

sealed class HomeViewState {
    object enabled: HomeViewState()

    object disabled: HomeViewState()

    object notVisibleStop: HomeViewState()

    object notVisibleStart: HomeViewState()
}