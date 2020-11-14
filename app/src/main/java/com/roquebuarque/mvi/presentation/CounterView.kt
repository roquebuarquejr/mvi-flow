package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.CounterModel

interface CounterView {

    fun render(model: CounterModel)

}