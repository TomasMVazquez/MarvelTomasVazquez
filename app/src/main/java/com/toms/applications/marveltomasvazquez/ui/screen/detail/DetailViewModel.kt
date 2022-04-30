package com.toms.applications.marveltomasvazquez.ui.screen.detail

import com.applications.toms.usecases.favorites.RemoveFromFavorites
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.usecases.favorites.SaveToFavorites
import com.applications.toms.data.onSuccess
import com.toms.applications.marveltomasvazquez.util.ScopedViewModel
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(private val getFavorites: GetFavorites,
                      private val saveToFavorites: SaveToFavorites,
                      private val removeFromFavorites: RemoveFromFavorites,
                      character: MyCharacter,
                      uiDispatcher: CoroutineDispatcher)
    : ScopedViewModel(uiDispatcher) {

    sealed class UiModel{
        object Loading: UiModel()
        object Favorite: UiModel()
        object NotFavorite: UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(UiModel.Loading)
    val model: StateFlow<UiModel> get() = _model

    private lateinit var databaseItems: List<MyCharacter>

    private var favorite: Boolean = false

    init {
        launch {
            getFavorites.prepare(null).collect { result ->
                result.onSuccess { flow ->
                    flow.collect { list ->
                        databaseItems = list
                        favorite = databaseItems.contains(character)
                        _model.value = if(favorite) Favorite else NotFavorite
                    }
                }
            }
        }
    }

    fun onFabClicked(character: MyCharacter){
        _model.value = Loading
        launch {
            favorite = if (favorite){
                removeFromFavorites.invoke(character)
                _model.value = NotFavorite
                false
            }else {
                saveToFavorites.invoke(character)
                _model.value = Favorite
                true
            }
        }

    }
}