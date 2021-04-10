package com.toms.applications.marveltomasvazquez.util

import androidx.lifecycle.Observer

/**
 * Reduce boilerplate on viewModels
 * Allowing to go through a variable just once (useful for navigation)
 */
open class Event <out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(eventWrapper: Event<T>?) {
        eventWrapper?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}