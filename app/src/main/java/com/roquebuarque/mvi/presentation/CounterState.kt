package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.Counter


data class CounterState(
    val counter: Counter,
    val syncState: CounterSyncState
)

sealed class CounterSyncState {
    object Loading : CounterSyncState()
    object Content : CounterSyncState()
    data class Message(val msg: String) : CounterSyncState()
}
/*
@startuml
   @startuml
    [*] --> LOADING :EXECUTING
   LOADING --> CONTENT : SUCCESS
   CONTENT : counter:Counter
   LOADING --> MESSAGE : ERROR
   MESSAGE : msg:String
    @enduml
@enduml
 */