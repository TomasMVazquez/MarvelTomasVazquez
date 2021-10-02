package com.toms.applications.marveltomasvazquez.ui.screen.search

import android.text.Editable
import androidx.lifecycle.ViewModel
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.SearchRepository
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.applications.toms.domain.Result as Character
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.Scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel(), Scope by Scope.ImplementJob() {

    sealed class UiModel {
        object None: UiModel()
        object Loading: UiModel()
        class Content(val characters: List<Character>): UiModel()
        class ErrorWatcher(val infoState: InfoState): UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    private val _navigation = MutableStateFlow<Event<Character?>>(Event(null))
    val navigation: StateFlow<Event<Character?>> get() = _navigation

    init {
        initScope()
        _model.value = None
    }

    fun onSearchBtnClicked(value: Editable) {
        _model.value = Loading
        launch {
            searchRepository.getCharacters(value.toString()).collect { result ->
                result.onSuccess {
                    if (it.data.results.isEmpty())
                        _model.value = ErrorWatcher(InfoState.SEARCH_EMPTY)
                    else
                        _model.value = Content(it.data.results)
                }
                result.onFailure {
                    _model.value = ErrorWatcher(InfoState.OTHER)
                }
            }
        }
    }

    fun onCharacterClicked(character: Character) {
        _navigation.value = Event(character)
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

}