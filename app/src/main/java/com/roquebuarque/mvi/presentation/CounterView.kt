package com.roquebuarque.mvi.presentation

interface CounterView {

    fun updateCounter(value: Int)

    fun loading(isLoading: Boolean)

    fun error(msg: String)

}