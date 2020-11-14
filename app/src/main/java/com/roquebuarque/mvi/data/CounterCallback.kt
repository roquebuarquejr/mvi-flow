package com.roquebuarque.mvi.data

interface CounterCallback {

    fun onSuccess(counter: Counter)

    fun onError(throwable: Throwable)
}