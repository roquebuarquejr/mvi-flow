package com.roquebuarque.mvi

import com.nhaarman.mockitokotlin2.given
import com.roquebuarque.mvi.data.Counter
import com.roquebuarque.mvi.presentation.CounterState
import com.roquebuarque.mvi.presentation.CounterSyncState
import com.roquebuarque.mvi.presentation.reducer.CounterAction
import com.roquebuarque.mvi.presentation.reducer.CounterReducer
import org.junit.Assert.assertTrue
import org.junit.Test

class CounterReducerTest {

    @Test
    fun test_loading(){
        //Given
        val action = CounterAction.Executing
        val currentState = CounterState(Counter(1), CounterSyncState.Content)

        //When
        val newState = CounterReducer().invoke(currentState, action)

        //Then
        assertTrue(newState.syncState == CounterSyncState.Loading)
    }

    @Test
    fun test_content(){
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
    fun test_message(){
        //Given
        val action = CounterAction.Error("deu ruim")
        val currentState = CounterState(Counter(1), CounterSyncState.Loading)

        //When
        val newState = CounterReducer().invoke(currentState, action)

        //Then
        assertTrue(newState.counter.value == 1)
        assertTrue(newState.syncState == CounterSyncState.Message("deu ruim"))
    }
}
