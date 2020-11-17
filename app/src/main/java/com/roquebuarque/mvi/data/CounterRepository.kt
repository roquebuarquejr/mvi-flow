package com.roquebuarque.mvi.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@FlowPreview
@Singleton
class CounterRepository @Inject constructor() {

    private val _counter = MutableStateFlow(Counter(value = 0))
    val counter: Flow<Counter> = _counter

    fun increase(oldValue: Int): Flow<Counter> {
        return flow {
            delay(2000)
            val newValue = oldValue + 1
            val counter = Counter(value = newValue)
            _counter.emit(counter)
            emit(counter)
        }.flowOn(Dispatchers.IO)
    }

    fun decrease(oldValue: Int): Flow<Counter> {
        return flow {
            delay(2000)
            val newValue = oldValue - 1
            val counter = Counter(value = newValue)
            _counter.emit(counter)
            emit(counter)
        }.flowOn(Dispatchers.IO)
    }

}