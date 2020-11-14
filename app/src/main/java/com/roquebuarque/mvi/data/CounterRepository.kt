package com.roquebuarque.mvi.data

object CounterRepository {

    private val counter = Counter(0)

    fun increase(callback: CounterCallback) {
        callback.onSuccess(counter.value++)
    }

    fun decrease(callback: CounterCallback) {
        callback.onSuccess(counter.value--)
    }
}