package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.text.Editable
import androidx.lifecycle.*
import com.toms.applications.marveltomasvazquez.domain.Character
import com.toms.applications.marveltomasvazquez.repository.FavoriteRepository
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.Scope
import com.toms.applications.marveltomasvazquez.util.filter
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel(), Scope by Scope.ImplementJob() {

    sealed class UiModel {
        object Loading: UiModel()
        class Content(val characters: LiveData<List<Character>>): UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Character>>()
    val navigation: LiveData<Event<Character>> get() = _navigation

    init {
        initScope()
        _model.value = Loading
        launch {
            _model.value = Content(favoriteRepository.getCharacters())
        }
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

    fun onCharacterClicked(character: Character) {
        _navigation.value = Event(character)
    }

    fun onSearchCharacter(value: Editable?) {
        _model.value = Loading
        launch {
            if (value.isNullOrEmpty()){
                _model.value = Content(favoriteRepository.getCharacters())
            }else{
                _model.value = Content(favoriteRepository.searchCharacters(value.toString()))
            }
        }
    }

}