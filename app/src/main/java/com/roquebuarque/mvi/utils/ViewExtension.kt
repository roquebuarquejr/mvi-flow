package com.roquebuarque.mvi.utils

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


@FlowPreview
@ExperimentalCoroutinesApi
fun View.setOnClickListenerFlow() = callbackFlow {
    val listener = View.OnClickListener { offer(Unit) }
    setOnClickListener(listener)
    awaitClose {
        setOnClickListener(null)
    }
}


