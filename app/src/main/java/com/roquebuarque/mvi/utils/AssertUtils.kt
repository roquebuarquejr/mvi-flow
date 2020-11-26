package com.roquebuarque.mvi.utils


import com.roquebuarque.mvi.BuildConfig

fun <State, Action> Boolean.guard(state: State, action: Action) {
    if (BuildConfig.DEBUG && !this) {
        error("Assertion failed for \nState: $state and \nAction: $action")
    }
}