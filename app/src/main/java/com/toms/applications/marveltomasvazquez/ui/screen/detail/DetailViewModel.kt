package com.toms.applications.marveltomasvazquez.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applications.toms.usecases.DeleteFavorite
import com.applications.toms.usecases.GetFavorites
import com.applications.toms.usecases.SaveFavorite
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem as Character
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Scope
import kotlinx.coroutines.launch

class DetailViewModel(private val getFavorites: GetFavorites,
                      private val saveFavorite: SaveFavorite,
                      private val deleteFavorite: DeleteFavorite,
                      character: Character): ViewModel(), Scope by Scope.ImplementJob() {

    sealed class UiModel{
        object Loading: UiModel()
        object Favorite: UiModel()
        object NotFavorite: UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = Loading
            return _model
        }

    private lateinit var databaseItems: List<Character>

    private var favorite: Boolean = false

    init {
        initScope()
        launch {
            databaseItems = getFavorites.invoke(null).map { it.asDatabaseModel() }
            favorite = databaseItems.contains(character)
            _model.value = if(favorite) Favorite else NotFavorite
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