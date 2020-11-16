package com.roquebuarque.mvi.redux

typealias Reducer<State, Action> = (oldState: State, action: Action) -> State