package com.roquebuarque.mvi.data

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@FlowPreview
@Singleton
class CounterRepository @Inject constructor() {

    private var _counter = MutableStateFlow(Counter(value = 0))

    suspend fun increase(): Counter {
        val newValue = _counter.value.value  + 1
        _counter.emit(Counter(value = newValue))
        return _counter.value
    }

    suspend fun decrease(): Counter {
        val newValue =  _counter.value.value - 1
        _counter.emit(Counter(value = newValue))
        return _counter.value
    }

}