package com.toms.applications.marveltomasvazquez.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.applications.toms.usecases.favorites.DeleteFavorite
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.usecases.favorites.SaveFavorite
import com.applications.toms.data.onSuccess
import com.applications.toms.depormas.utils.ScopedViewModel
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem as Character
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(private val getFavorites: GetFavorites,
                      private val saveFavorite: SaveFavorite,
                      private val deleteFavorite: DeleteFavorite,
                      character: Character,
                      uiDispatcher: CoroutineDispatcher)
    : ScopedViewModel(uiDispatcher) {

    sealed class UiModel{
        object Loading: UiModel()
        object Favorite: UiModel()
        object NotFavorite: UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(UiModel.Loading)
    val model: StateFlow<UiModel> get() = _model

    private lateinit var databaseItems: List<Character>

    private var favorite: Boolean = false

    init {
        launch {
            getFavorites.prepare(null).collect { result ->
                result.onSuccess { flow ->
                    flow.collect { list ->
                        databaseItems = list.map { it.asDatabaseModel() }
                        favorite = databaseItems.contains(character)
                        _model.value = if(favorite) Favorite else NotFavorite
                    }
                }
            }
        }
    }

    fun onFabClicked(character: Character){
        _model.value = Loading
        launch {
            favorite = if (favorite){
                deleteFavorite.invoke(character.id.toLong())
                _model.value = NotFavorite
                false
            }else {
                saveFavorite.invoke(character.asDomainModel())
                _model.value = Favorite
                true
            }
        }

    }
}