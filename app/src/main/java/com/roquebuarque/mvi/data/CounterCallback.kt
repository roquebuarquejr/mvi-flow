package com.roquebuarque.mvi.data

interface CounterCallback {

    fun onSuccess(value: Int)

    fun onError(throwable: Throwable)
}