package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.*
import com.roquebuarque.mvi.presentation.reducer.CounterAction
import com.roquebuarque.mvi.presentation.reducer.CounterActionCreator
import com.roquebuarque.mvi.presentation.reducer.CounterEvent
import com.roquebuarque.mvi.presentation.reducer.CounterReducer
import com.roquebuarque.mvi.redux.StateViewModel
import kotlinx.coroutines.*

@FlowPreview
@ExperimentalCoroutinesApi
class CounterViewModel(reducer: CounterReducer, actionCreator: CounterActionCreator)
    : StateViewModel<CounterState, CounterEvent, CounterAction>(
        initialState = CounterState(
            counter = Counter(0),
            syncState = CounterSyncState.Content
        ),
        initialEvent = CounterEvent.InitialEvent,
        reducer = reducer,
        action = actionCreator
    ) {

    companion object {
        fun create(
            reducer: CounterReducer = CounterReducer,
            actionCreator: CounterActionCreator = CounterActionCreator.create()
        ): CounterViewModel {
            return CounterViewModel(reducer, actionCreator)
        }
    }
}