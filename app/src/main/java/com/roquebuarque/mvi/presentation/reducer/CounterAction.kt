package com.roquebuarque.mvi.presentation.reducer

import com.roquebuarque.mvi.data.Counter

sealed class CounterAction {

    object Executing : CounterAction()

    data class Success(val counter: Counter) : CounterAction()

    data class Error(val msg: String) : CounterAction()
}