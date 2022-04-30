package com.toms.applications.marveltomasvazquez.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
): TestWatcher() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        testDispatcher.cleanupTestCoroutines()
        super.finished(description)
    }

}