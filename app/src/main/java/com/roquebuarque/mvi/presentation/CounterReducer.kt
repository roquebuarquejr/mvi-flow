package com.roquebuarque.mvi.presentation

import android.annotation.SuppressLint
import android.util.Log
import com.roquebuarque.mvi.data.CounterState
import com.roquebuarque.mvi.data.CounterSyncState
import com.roquebuarque.mvi.redux.Reducer

object CounterReducer : Reducer<CounterState, CounterAction> {

    private val TAG = CounterReducer::class.java.name

    @SuppressLint("Assert")
    override fun invoke(oldState: CounterState, action: CounterAction): CounterState {
        Log.d(TAG, "Action $action")
        Log.d(TAG, "State $oldState")
        return when (action) {
            CounterAction.Executing -> {
                assert(
                            oldState.syncState is CounterSyncState.Content ||
                            oldState.syncState is CounterSyncState.Message
                )
                // assertValue(oldState.syncState, CounterSyncState.Idle)
                oldState.copy(syncState = CounterSyncState.Loading)

            }
            is CounterAction.Success -> {
                assert(oldState.syncState == CounterSyncState.Loading)
                //  assertValue(oldState.syncState, CounterSyncState.Success)
                oldState.copy(
                    counter = action.counter,
                    syncState = CounterSyncState.Content
                )
            }
            is CounterAction.Error -> {
                assert(oldState.syncState is CounterSyncState.Loading)
                //  assertValue(oldState.syncState, CounterSyncState.Error)
                oldState.copy(syncState = CounterSyncState.Message(action.msg))
            }
        }
    }
}