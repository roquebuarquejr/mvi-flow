package com.roquebuarque.mvi.presentation.reducer

import com.roquebuarque.mvi.presentation.CounterState
import com.roquebuarque.mvi.presentation.CounterSyncState
import com.roquebuarque.mvi.redux.Reducer
import com.roquebuarque.mvi.utils.guard
import javax.inject.Inject

class CounterReducer @Inject constructor() : Reducer<CounterState, CounterAction> {

    override fun invoke(currentState: CounterState, action: CounterAction): CounterState {
        return when (action) {
            CounterAction.Executing -> {

                val isContentOrMessage =
                    currentState.syncState is CounterSyncState.Content ||
                            currentState.syncState is CounterSyncState.Message

                isContentOrMessage.guard(
                    state = currentState,
                    action = action
                )

                currentState.copy(syncState = CounterSyncState.Loading)
            }

            is CounterAction.Success -> {

                val isLoading = currentState.syncState == CounterSyncState.Loading
                isLoading.guard(
                    state = currentState,
                    action = action
                )

                currentState.copy(
                    counter = action.counter,
                    syncState = CounterSyncState.Content
                )
            }
            is CounterAction.Error -> {
                val isLoading = currentState.syncState == CounterSyncState.Loading

                isLoading.guard(
                    state = currentState,
                    action = action
                )
                
                currentState.copy(
                    syncState = CounterSyncState.Message(
                        action.msg
                    )
                )
            }
        }
    }
}