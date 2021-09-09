package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.text.Editable
import androidx.lifecycle.*
import com.applications.toms.usecases.GetFavorites
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem as Character
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.Scope
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavorites: GetFavorites): ViewModel(), Scope by Scope.ImplementJob() {

    sealed class UiModel {
        object Loading: UiModel()
        class Content(val characters: List<Character>): UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Character>>()
    val navigation: LiveData<Event<Character>> get() = _navigation

    init {
        initScope()
        _model.value = Loading
        launch {
            _model.value = Content(getFavorites.invoke(null).map { it.asDatabaseModel() })
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
            _model.value = Content(getFavorites.invoke(value.toString()).map { it.asDatabaseModel() })
        }
    }

}