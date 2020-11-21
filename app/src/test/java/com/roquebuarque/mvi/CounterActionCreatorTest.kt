package com.roquebuarque.mvi

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roquebuarque.mvi.data.Counter
import com.roquebuarque.mvi.data.CounterRepository
import com.roquebuarque.mvi.presentation.reducer.CounterAction
import com.roquebuarque.mvi.presentation.reducer.CounterActionCreator
import com.roquebuarque.mvi.presentation.reducer.CounterEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
@FlowPreview
class CounterActionCreatorTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val repository: CounterRepository = mock()
    private val actionCreator = CounterActionCreator(repository)

    @Test
    fun test_increase_event_success() {
        runBlocking {
            //Given
            val event = CounterEvent.Increase
            whenever(repository.increase()).thenReturn(Counter(1))

            //When
            val result = actionCreator.invoke(event)

            //Then
            assertTrue(result.count() == 2)
            assertTrue(result.take(2).toList().contains(CounterAction.Executing))
            assertTrue(result.take(2).toList().contains(CounterAction.Success(Counter(1))))
        }
    }

    @Test
    fun test_increase_event_error() {
        runBlocking {
            //Given
            val event = CounterEvent.Increase
            whenever(repository.increase()).thenThrow(IllegalArgumentException("test"))

            //When
            val result = actionCreator.invoke(event)

            //Then
            assertTrue(result.count() == 2)
            assertTrue(result.take(2).toList().contains(CounterAction.Executing))
            assertTrue(result.take(2).toList().contains(CounterAction.Error("deu ruim")))
        }
    }
}