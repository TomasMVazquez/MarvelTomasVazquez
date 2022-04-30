package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.text.Editable
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.favorites.GetFavorites
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.Content
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.Loading
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.ScopedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavorites: GetFavorites, uiDispatcher: CoroutineDispatcher) :
    ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val characters: List<MyCharacter>) : UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    private val _navigation = MutableStateFlow<Event<MyCharacter?>>(Event(null))
    val navigation: StateFlow<Event<MyCharacter?>> get() = _navigation

    init {
        _model.value = Loading
        launch {
            getFavorites.prepare(null).collect { result ->
                result.onSuccess { flow ->
                    flow.collect { list ->
                        _model.value = Content(list)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

    fun onCharacterClicked(character: MyCharacter) {
        _navigation.value = Event(character)
    }

    fun onSearchCharacter(value: Editable?) {
        _model.value = Loading
        launch {
            getFavorites.prepare(value.toString()).collect { result ->
                result.onSuccess { flow ->
                    flow.collect { list ->
                        _model.value = Content(list)
                    }
                }
            }
        }
    }

}