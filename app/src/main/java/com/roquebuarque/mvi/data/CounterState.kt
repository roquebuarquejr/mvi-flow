package com.roquebuarque.mvi.data

sealed class CounterState {

    data class Content(
        val counter: Counter
    ) : CounterState()

    object Loading : CounterState()

    data class Error(
        val msg: String
    ) : CounterState()

    data class Message(
        val msg: String
    ) : CounterState()
}