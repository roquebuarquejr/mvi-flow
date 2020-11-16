package com.roquebuarque.mvi.data

data class CounterState(
    val counter: Counter,
    val syncState: CounterSyncState
)

sealed class CounterSyncState {
    object Loading : CounterSyncState()
    object Content : CounterSyncState()
    data class Message(val msg: String) : CounterSyncState()
}