package com.toms.applications.marveltomasvazquez.ui.screen.search

import android.text.Editable
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.ScopedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object None : UiModel()
        object Loading : UiModel()
        class Content(val characters: List<MyCharacter>) : UiModel()
        class ErrorWatcher(val infoState: InfoState) : UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    private val _navigation = MutableStateFlow<Event<MyCharacter?>>(Event(null))
    val navigation: StateFlow<Event<MyCharacter?>> get() = _navigation

    init {
        _model.value = None
    }

    fun onSearchBtnClicked(value: Editable) {
        _model.value = Loading
        launch {
            searchRepository.getCharacters(value.toString())
                .onSuccess {
                    if (it.isEmpty())
                        _model.value = ErrorWatcher(InfoState.SEARCH_EMPTY)
                    else
                        _model.value = Content(it)
                }
                .onFailure {
                    _model.value = ErrorWatcher(InfoState.OTHER)
                }
        }
    }

    fun onCharacterClicked(character: MyCharacter) {
        _navigation.value = Event(character)
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

}
