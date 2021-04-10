package com.toms.applications.marveltomasvazquez.ui.screen.search

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toms.applications.marveltomasvazquez.domain.Character
import com.toms.applications.marveltomasvazquez.network.model.asDomainModel
import com.toms.applications.marveltomasvazquez.repository.SearchRepository
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.Scope
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel(), Scope by Scope.ImplementJob() {

    sealed class UiModel {
        object None: UiModel()
        object Loading: UiModel()
        class Content(val characters: List<Character>): UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Character>>()
    val navigation: LiveData<Event<Character>> get() = _navigation

    init {
        initScope()
        _model.value = None
    }

    fun onSearchBtnClicked(value: Editable) {
        _model.value = Loading
        launch {
            _model.value = Content(searchRepository.getCharacters(value.toString()).data.results.asDomainModel())
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