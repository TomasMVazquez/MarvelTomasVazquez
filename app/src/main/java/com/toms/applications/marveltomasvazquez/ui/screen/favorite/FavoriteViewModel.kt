package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.text.Editable
import androidx.lifecycle.*
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.data.onSuccess
import com.applications.toms.depormas.utils.ScopedViewModel
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem as Character
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavorites: GetFavorites, uiDispatcher: CoroutineDispatcher)
    : ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading: UiModel()
        class Content(val characters: List<Character>): UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    private val _navigation = MutableStateFlow<Event<Character?>>(Event(null))
    val navigation: StateFlow<Event<Character?>> get() = _navigation

    init {
        _model.value = Loading
        launch {
            getFavorites.prepare(null).collect { result ->
                result.onSuccess { flow ->
                    flow.collect { list ->
                        _model.value = Content(list.map{it.asDatabaseModel()})
                    }
                }
            }
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
            getFavorites.prepare(value.toString()).collect { result ->
                result.onSuccess { flow ->
                    flow.collect { list ->
                        _model.value = Content(list.map{it.asDatabaseModel()})
                    }
                }
            }
        }
    }

}