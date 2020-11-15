package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.CounterModel
import com.roquebuarque.mvi.data.CounterState

interface CounterView {

    fun render(state: CounterState)

}