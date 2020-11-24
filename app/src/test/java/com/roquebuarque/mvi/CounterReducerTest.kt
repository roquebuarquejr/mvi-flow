package com.roquebuarque.mvi

import com.roquebuarque.mvi.data.Counter
import com.roquebuarque.mvi.presentation.CounterState
import com.roquebuarque.mvi.presentation.CounterSyncState
import com.roquebuarque.mvi.presentation.reducer.CounterAction
import com.roquebuarque.mvi.presentation.reducer.CounterReducer
import org.junit.Assert.assertTrue
import org.junit.Test

class CounterReducerTest {

    @Test
    fun test_loading() {
        //Given
        val action = CounterAction.Executing
        val currentState = CounterState(Counter(1), CounterSyncState.Content)

        //When
        val newState = CounterReducer().invoke(currentState, action)

        //Then
        assertTrue(newState.syncState == CounterSyncState.Loading)
    }

    @Test
    fun test_loading_guard_error() {
        //Given
        val action = CounterAction.Executing
        val currentState = CounterState(Counter(1), CounterSyncState.Loading)
        var isOnError = false

        //When
        try {
             CounterReducer().invoke(currentState, action)
        } catch (e: IllegalStateException) {
            isOnError = true
        }

        //Then
        assertTrue(isOnError)
    }

    @ExperimentalStdlibApi
    @Test
    fun test_content() {
        //Given
        val action = CounterAction.Success(Counter(2))
        val currentState = CounterState(Counter(1), CounterSyncState.Loading)

        //When
        val newState = CounterReducer().invoke(currentState, action)

        //Then
        assertTrue(newState.syncState == CounterSyncState.Content)
        assertTrue(newState.counter.value == 2)
    }

    @Test
    fun test_content_guard_error() {
        //Given
            val action = CounterAction.Success(Counter(1))
        val currentState = CounterState(Counter(0), CounterSyncState.Message("deu ruim"))
        var isOnError = false

        //When
        try {
            CounterReducer().invoke(currentState, action)
        } catch (e: IllegalStateException) {
            isOnError = true
        }

        //Then
        assertTrue(isOnError)
    }


    @Test
    fun test_message() {
        //Given
        val action = CounterAction.Error("deu ruim")
        val currentState = CounterState(Counter(1), CounterSyncState.Loading)

        //When
        val newState = CounterReducer().invoke(currentState, action)

        //Then
        assertTrue(newState.counter.value == 1)
        assertTrue(newState.syncState == CounterSyncState.Message("deu ruim"))
    }

    @Test
    fun test_message_guard_error() {
        //Given
        val action = CounterAction.Error("deu ruim")
        val currentState = CounterState(Counter(0), CounterSyncState.Content)
        var isOnError = false

        //When
        try {
            CounterReducer().invoke(currentState, action)
        } catch (e: IllegalStateException) {
            isOnError = true
        }

        //Then
        assertTrue(isOnError)
    }
}
