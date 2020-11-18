package com.roquebuarque.mvi.redux

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
abstract class StateViewModel<State, Event, Action>(
    initialEvent: Event,
    private val initialState: State, private val reducer: (State, Action) -> State,
    private val action: (Event) -> Flow<Action>
) : ViewModel() {

    private val event = MutableStateFlow(initialEvent)
    val state: StateFlow<State> = toState(viewModelScope)

    suspend fun process(event: Flow<Event>) {
        event.collect {
            this.event.emit(it)
        }
    }

    private fun toState(scope: CoroutineScope): StateFlow<State> {
        return event
            .flatMapLatest { action.invoke(it) }
            .map { reducer.invoke(state.value, it) }
            .onCompletion { Log.d(StateViewModel::class.java.name, "onCompletion for $state") }
            .stateIn(scope, SharingStarted.Lazily, initialState)

    }

}

