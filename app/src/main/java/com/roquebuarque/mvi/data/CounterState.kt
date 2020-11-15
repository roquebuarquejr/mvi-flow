package com.roquebuarque.mvi.data

sealed class CounterState {

    data class Content(
        val value: Int
    ) : CounterState()

    object Loading : CounterState()

    data class Error(
        val msg: String
    ) : CounterState()
}