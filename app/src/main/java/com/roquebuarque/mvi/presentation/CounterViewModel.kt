package com.roquebuarque.mvi.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roquebuarque.mvi.data.Counter
import com.roquebuarque.mvi.data.CounterCallback
import com.roquebuarque.mvi.data.CounterRepository

class CounterViewModel: ViewModel() {

    private val repository = CounterRepository
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val content = MutableLiveData<Int>()


    fun increase(){
        loading.value = true
        repository.increase(object : CounterCallback{
            override fun onSuccess(counter: Counter) {
                loading.value = false
                content.value = counter.value
            }

            override fun onError(throwable: Throwable) {
                loading.value = false
                error.value = throwable.message ?: "error"
            }

        })
    }


}