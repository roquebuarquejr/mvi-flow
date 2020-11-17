package com.roquebuarque.mvi.presentation.reducer

sealed class CounterEvent {

    data class Analytics(val str: String) : CounterEvent()

    object InitialEvent : CounterEvent()

    data class Increase(val value: Int) : CounterEvent()

    data class Decrease(val value: Int) : CounterEvent()

}