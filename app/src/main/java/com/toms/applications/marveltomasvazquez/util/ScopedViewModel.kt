package com.toms.applications.marveltomasvazquez.util

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.toms.applications.marveltomasvazquez.util.Scope
import kotlinx.coroutines.CoroutineDispatcher

abstract class ScopedViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    Scope by Scope.ImplementJob(uiDispatcher) {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}