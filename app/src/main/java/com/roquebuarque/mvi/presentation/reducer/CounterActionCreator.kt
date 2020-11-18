package com.roquebuarque.mvi.presentation.reducer

import android.util.Log
import com.roquebuarque.mvi.data.CounterRepository
import com.roquebuarque.mvi.redux.Action
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
class CounterActionCreator @Inject constructor(
    private val repository: CounterRepository
) : Action<CounterEvent, CounterAction> {

    override fun invoke(event: CounterEvent): Flow<CounterAction> {
        return when (event) {
            CounterEvent.InitialEvent -> initial()
            is CounterEvent.Increase -> increase(event.value)
            is CounterEvent.Decrease -> decrease(event.value)
            is CounterEvent.Analytics -> analytics(event.str)
        }
    }

    private fun analytics(str: String): Flow<CounterAction> {
       return flow {
           Log.d("Roque", "Roque")
            emit(CounterAction.SideEffect(str))
        }.drop(1)
    }

    private fun initial(): Flow<CounterAction> {
        return repository.counter
            .map { CounterAction.Success(it) as CounterAction }
           .onStart { emit(CounterAction.Fetch) }
    }

    private fun increase(oldValue: Int): Flow<CounterAction> {
        return repository.increase(oldValue)
            .map { CounterAction.Success(it) as CounterAction }
            .onStart { emit(CounterAction.Increase) }

    }

    private fun decrease(oldValue: Int): Flow<CounterAction> {
        return repository.decrease(oldValue)
            .map { CounterAction.Success(it) as CounterAction }
            .onStart { emit(CounterAction.Decrease) }

    }
}