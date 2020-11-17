package com.roquebuarque.mvi.presentation.reducer

import android.annotation.SuppressLint
import android.util.Log
import com.roquebuarque.mvi.presentation.CounterState
import com.roquebuarque.mvi.presentation.CounterSyncState
import com.roquebuarque.mvi.redux.Reducer

object CounterReducer : Reducer<CounterState, CounterAction> {

    private val TAG = CounterReducer::class.java.name

    @SuppressLint("Assert")
    override fun invoke(oldState: CounterState, action: CounterAction): CounterState {
        Log.d(TAG, "Action $action")
        Log.d(TAG, "State $oldState")
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
        }
    }
}