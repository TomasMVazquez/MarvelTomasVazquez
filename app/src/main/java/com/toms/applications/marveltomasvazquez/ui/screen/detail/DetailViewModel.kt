package com.toms.applications.marveltomasvazquez.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toms.applications.marveltomasvazquez.domain.Character
import com.toms.applications.marveltomasvazquez.domain.asDatabaseModel
import com.toms.applications.marveltomasvazquez.repository.FavoriteRepository
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Scope
import kotlinx.coroutines.launch

class DetailViewModel(private val favoriteRepository: FavoriteRepository,character: Character): ViewModel(), Scope by Scope.ImplementJob() {

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
            databaseItems = favoriteRepository.getCharactersList()
            favorite = databaseItems.contains(character)
            _model.value = if(favorite) Favorite else NotFavorite
        }
    }

    fun onFabClicked(character: Character){
        _model.value = Loading
        launch {
            favorite = if (favorite){
                favoriteRepository.deleteCharacter(character.id.toLong())
                _model.value = NotFavorite
                false
            }else {
                favoriteRepository.saveCharacter(character.asDatabaseModel())
                _model.value = Favorite
                true
            }
        }

    }
}