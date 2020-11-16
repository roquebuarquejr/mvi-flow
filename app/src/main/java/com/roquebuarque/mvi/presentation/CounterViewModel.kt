package com.roquebuarque.mvi.presentation

import android.util.Log
import androidx.lifecycle.*
import com.roquebuarque.mvi.data.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class CounterViewModel : ViewModel() {

    private val repository = CounterRepository
    private val intentStateFlow = MutableStateFlow<CounterIntent>(CounterIntent.InitialState)

    fun intent(viewIntent: Flow<CounterIntent>) {
        viewModelScope.launch {
            viewIntent
                .collect {
                intentStateFlow.emit(it)
            }
        }
    }

    fun stateFlow(scope: CoroutineScope): Flow<CounterState> {
        return intentToState()
            .stateIn(scope, SharingStarted.Lazily, CounterState.Loading)
    }

    private fun intentToState(): Flow<CounterState> {
        return intentStateFlow
            .onEach { Log.d(TAG, "View Intent $it") }
            .flatMapLatest {
                when (it) {
                    CounterIntent.InitialState -> initialState()
                    is CounterIntent.Increase -> increase(it.value)
                    is CounterIntent.Decrease -> decrease(it.value)
                }
            }
            .onEach { Log.d(TAG, "State $it") }
    }

    private fun fetchCounter(): Flow<CounterState> {
        return repository.counter.map { CounterState.Content(it) }
    }

    private fun initialState(): Flow<CounterState> =
        fetchCounter()
            .onStart { emit(CounterState.Loading) }

    private fun increase(oldValue: Int): Flow<CounterState> {
        return repository
            .increase(oldValue)
            .map { CounterState.Content(it) as CounterState }
            .onStart { emit(CounterState.Loading) }
            .catch { emit(CounterState.Message("Something went wrong")) }

    }


    private fun decrease(oldValue: Int): Flow<CounterState> {
        return repository.decrease(oldValue)
            .map { CounterState.Content(it) as CounterState}
            .onStart { emit(CounterState.Loading) }
            .catch { emit(CounterState.Message("Something went wrong")) }

    }

    companion object {
        private val TAG = CounterViewModel::class.java.name
    }
}


sealed class CounterIntent {

    object InitialState : CounterIntent()

    data class Increase(val value: Int) : CounterIntent()

    data class Decrease(val value: Int) : CounterIntent()

}