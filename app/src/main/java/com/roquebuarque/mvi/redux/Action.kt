package com.roquebuarque.mvi.redux

import kotlinx.coroutines.flow.Flow

typealias Action<Event, Action> = (event: Event) -> Flow<Action>