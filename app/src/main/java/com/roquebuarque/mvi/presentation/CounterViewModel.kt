package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.*
import com.roquebuarque.mvi.redux.StateViewModel
import kotlinx.coroutines.*

@FlowPreview
@ExperimentalCoroutinesApi
class CounterViewModel(scope: CoroutineScope) :
    StateViewModel<CounterState, CounterEvent, CounterAction>(
        scope = scope,
        initialState = CounterState(
            counter = Counter(0),
            syncState = CounterSyncState.Content
        ),
        initialEvent = CounterEvent.InitialEvent,
        reducer = CounterReducer,
        action = CounterActionCreator.create()
    )


sealed class CounterAction {

    object Executing : CounterAction()

    data class Success(val counter: Counter) : CounterAction()

    data class Error(val msg: String) : CounterAction()
}


sealed class CounterEvent {

    object InitialEvent : CounterEvent()

    data class Increase(val value: Int) : CounterEvent()

    data class Decrease(val value: Int) : CounterEvent()

}