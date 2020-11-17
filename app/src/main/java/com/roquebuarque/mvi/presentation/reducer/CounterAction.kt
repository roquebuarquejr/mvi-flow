package com.roquebuarque.mvi.presentation.reducer

import com.roquebuarque.mvi.data.Counter

sealed class CounterAction {

    data class SideEffect(val sg : String) : CounterAction()

    object Fetch : CounterAction()

    object Increase : CounterAction()

    object Decrease : CounterAction()

    data class Success(val counter: Counter) : CounterAction()

    data class Error(val msg: String) : CounterAction()
}