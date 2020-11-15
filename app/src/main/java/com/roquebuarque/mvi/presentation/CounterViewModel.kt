package com.roquebuarque.mvi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roquebuarque.mvi.data.*

class CounterViewModel : ViewModel() {

    private val repository = CounterRepository
    private val _state = MutableLiveData<CounterState>()
    val state: LiveData<CounterState> = _state

    fun increase() {
        _state.value = CounterState.Loading
        repository.increase(object : CounterCallback {
            override fun onSuccess(counter: Counter) {
                _state.value = CounterState.Content(counter.value)
            }

            override fun onError(throwable: Throwable) {
                _state.value = CounterState.Error(
                    throwable.message ?: "error"
                )
            }
        })
    }

    fun decrease() {
        _state.value = CounterState.Loading
        repository.decrease(object : CounterCallback {
            override fun onSuccess(counter: Counter) {
                _state.value = CounterState.Content(counter.value)
            }

            override fun onError(throwable: Throwable) {
                _state.value = CounterState.Error(
                    throwable.message ?: "error"
                )
            }
        })
    }


}