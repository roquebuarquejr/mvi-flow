package com.roquebuarque.mvi.presentation.reducer

import com.roquebuarque.mvi.data.Counter
import com.roquebuarque.mvi.data.CounterRepository
import com.roquebuarque.mvi.redux.Action
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class CounterActionCreator @Inject constructor(
    private val repository: CounterRepository
) : Action<CounterEvent, CounterAction> {

    override fun invoke(event: CounterEvent): Flow<CounterAction> {
        return flow {
            when (event) {
                is CounterEvent.Increase -> increase()
                is CounterEvent.Decrease -> decrease()
            }.run {
                emit(CounterAction.Success(this) as CounterAction)
            }
        }
            .buffer(0)
            .onStart { emit(CounterAction.Executing) }
            .catch { emit(CounterAction.Error("erro inesperado")) }
            .flowOn(Dispatchers.IO)
    }

    private suspend fun increase(): Counter {
        val result = repository.increase()
        delay(2000)
        return result
    }

    private suspend fun decrease(): Counter {
        val result = repository.decrease()
        delay(2000)
        return result
    }
}