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
    override fun invoke(oldState: CounterState, action: CounterAction): CounterState {
        Log.d(TAG, "Old State $oldState")
        Log.d(TAG, "Action $action")
        return when (action) {
            CounterAction.Fetch -> {
                assert(
                    oldState.syncState is CounterSyncState.Content ||
                            oldState.syncState is CounterSyncState.Message
                )
                oldState.copy(syncState = CounterSyncState.Loading)

            }
            CounterAction.Increase -> {
                assert(
                    oldState.syncState is CounterSyncState.Content ||
                            oldState.syncState is CounterSyncState.Message
                )
                oldState.copy(syncState = CounterSyncState.Loading)
            }
            CounterAction.Decrease -> {
                assert(
                    oldState.syncState is CounterSyncState.Content ||
                            oldState.syncState is CounterSyncState.Message
                )
                oldState.copy(syncState = CounterSyncState.Loading)
            }
            is CounterAction.Success -> {
                assert(oldState.syncState == CounterSyncState.Loading)
                oldState.copy(
                    counter = action.counter,
                    syncState = CounterSyncState.Content
                )
            }
            is CounterAction.Error -> {
                assert(oldState.syncState is CounterSyncState.Loading)
                oldState.copy(
                    syncState = CounterSyncState.Message(
                        action.msg
                    )
                )
            }
            is CounterAction.SideEffect -> oldState
        }.also {
            Log.d(TAG, "New State $it")
        }
    }
}