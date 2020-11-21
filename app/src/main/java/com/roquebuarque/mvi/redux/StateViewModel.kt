package com.roquebuarque.mvi.redux

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
abstract class StateViewModel<State, Event, Action>(
    private val initialState: State,
    private val reducer: (State, Action) -> State,
    private val action: (Event) -> Flow<Action>
) : ViewModel() {

    companion object {
        private val TAG = StateViewModel::class.java.name
    }

    private val event = Channel<Event>()
    val state: StateFlow<State> = toState(viewModelScope)

    suspend fun process(event: Flow<Event>) {
        event.collect {
            this.event.send(it)
        }
    }

    private fun toState(scope: CoroutineScope): StateFlow<State> {
        return event
            .receiveAsFlow()
            .onEach { Log.d(TAG, "Event $it") }
            .flatMapConcat { action.invoke(it) }
            .onEach { Log.d(TAG, "Action $it") }
            .map { reducer.invoke(state.value, it) }
            .onEach { Log.d(TAG, "State $it") }
            .onCompletion { Log.d(StateViewModel::class.java.name, "onCompletion for $state") }
            .stateIn(scope, SharingStarted.Lazily, initialState)
    }

}

