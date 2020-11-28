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

    //Producer
    private val event = MutableSharedFlow<Event>()//Could be just channel

    //Consumer
    val state: StateFlow<State> = toState()

    suspend fun process(event: Flow<Event>) {
        event.collect {
            this.event.emit(it)//suspend
        }
    }

    private fun toState(): StateFlow<State> {
        return event
            .onEach { Log.d(TAG, "Event $it") }
            .flatMapConcat { event-> action(event) }
            .distinctUntilChanged()
            .onEach { Log.i(TAG, "Action $it") }
            .map {action-> reducer(state.value, action) }
            .onEach { Log.w(TAG, "State $it") }
            .onCompletion { Log.d(StateViewModel::class.java.name, "onCompletion for $state") }
            .stateIn(viewModelScope, SharingStarted.Lazily, initialState)
    }
}

