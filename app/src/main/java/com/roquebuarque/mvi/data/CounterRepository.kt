package com.roquebuarque.mvi.data

import java.lang.IllegalArgumentException

object CounterRepository {

     val counter = Counter(0)

    fun increase(callback: CounterCallback) {
        val newValue =  counter.value + 1
        counter.value++
        callback.onSuccess(counter.copy(value = newValue))
    }

    fun decrease(callback: CounterCallback) {
        callback.onError(IllegalArgumentException("deu ruim"))
    }
}