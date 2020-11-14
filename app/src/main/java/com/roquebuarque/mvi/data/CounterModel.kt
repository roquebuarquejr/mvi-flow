package com.roquebuarque.mvi.data

data class CounterModel(
    val counter: Counter?= null,
    val isLoading: Boolean,
    val throwable: Throwable? = null
)
