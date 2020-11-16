package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.CounterRepository
import com.roquebuarque.mvi.redux.Action
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
class CounterActionCreator(private val repository: CounterRepository) :
    Action<CounterEvent, CounterAction> {

    companion object {

        fun create(repository: CounterRepository = CounterRepository): CounterActionCreator {
            return CounterActionCreator(repository)
        }
    }

    override fun invoke(event: CounterEvent): Flow<CounterAction> {
        return when (event) {
            CounterEvent.InitialEvent -> initial()
            is CounterEvent.Increase -> increase(event.value)
            is CounterEvent.Decrease -> decrease(event.value)
        }
    }

    private fun initial(): Flow<CounterAction> {
        return repository.counter
            .map { CounterAction.Success(it) as CounterAction }
            .onStart { emit(CounterAction.Executing) }

    }

    private fun increase(oldValue: Int): Flow<CounterAction> {
        return repository.increase(oldValue)
            .map { CounterAction.Success(it) as CounterAction }
            .onStart { emit(CounterAction.Executing) }

    }

    private fun decrease(oldValue: Int): Flow<CounterAction> {
        return repository.decrease(oldValue)
            .map { CounterAction.Success(it) as CounterAction }
            .onStart { emit(CounterAction.Executing) }

    }

}