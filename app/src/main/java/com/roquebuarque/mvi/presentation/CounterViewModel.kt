package com.roquebuarque.mvi.presentation

import androidx.hilt.lifecycle.ViewModelInject
import com.roquebuarque.mvi.data.*
import com.roquebuarque.mvi.presentation.reducer.CounterAction
import com.roquebuarque.mvi.presentation.reducer.CounterActionCreator
import com.roquebuarque.mvi.presentation.reducer.CounterEvent
import com.roquebuarque.mvi.presentation.reducer.CounterReducer
import com.roquebuarque.mvi.redux.StateViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class CounterViewModel @ViewModelInject constructor(
    reducer: CounterReducer,
    actionCreator: CounterActionCreator
) : StateViewModel<CounterState, CounterEvent, CounterAction>(
    initialState = CounterState(
        counter = Counter(0),
        syncState = CounterSyncState.Content
    ),
    initialEvent = CounterEvent.InitialEvent,
    reducer = reducer,
    action = actionCreator
)

