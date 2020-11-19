package com.roquebuarque.mvi.presentation.reducer

import android.annotation.SuppressLint
import android.util.Log
import com.roquebuarque.mvi.presentation.CounterState
import com.roquebuarque.mvi.presentation.CounterSyncState
import com.roquebuarque.mvi.redux.Reducer
import javax.inject.Inject

class CounterReducer @Inject constructor() : Reducer<CounterState, CounterAction> {

    companion object{
        private val TAG = CounterReducer::class.java.name
    }

    @SuppressLint("Assert")
    override fun invoke(currentState: CounterState, action: CounterAction): CounterState {
        Log.d(TAG, "Current State $currentState")
        Log.d(TAG, "Action $action")
        return when (action) {
            CounterAction.Executing -> {
                assert(
                    currentState.syncState is CounterSyncState.Content ||
                            currentState.syncState is CounterSyncState.Message
                )
                currentState.copy(syncState = CounterSyncState.Loading)
            }
            is CounterAction.Success -> {
                assert(currentState.syncState == CounterSyncState.Loading)
                currentState.copy(
                    counter = action.counter,
                    syncState = CounterSyncState.Content
                )
            }
            is CounterAction.Error -> {
                assert(currentState.syncState is CounterSyncState.Loading)
                currentState.copy(
                    syncState = CounterSyncState.Message(
                        action.msg
                    )
                )
            }
        }.also {
            Log.d(TAG, "New State $it")
        }
    }
}