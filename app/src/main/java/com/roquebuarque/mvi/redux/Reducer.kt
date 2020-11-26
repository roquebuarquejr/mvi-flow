package com.roquebuarque.mvi.redux

typealias Reducer<State, Action> = (currentState: State, action: Action) -> State