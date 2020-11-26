package com.roquebuarque.mvi.presentation.reducer

sealed class CounterEvent {

    object Increase : CounterEvent()

    object Decrease : CounterEvent()

}