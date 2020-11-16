package com.roquebuarque.mvi.redux

import kotlinx.coroutines.flow.Flow

typealias Action<Event, Action> = (intent: Event) -> Flow<Action>