package com.toms.applications.marveltomasvazquez.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.characters.GetCharacterDetailUseCase
import com.applications.toms.usecases.favorites.GetFavoritesUseCase
import com.applications.toms.usecases.favorites.RemoveFromFavoritesUseCase
import com.applications.toms.usecases.favorites.SaveToFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val saveToFavoritesUseCase: SaveToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val characterId: String
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private var favorite: Boolean = false

    fun getData() {
        viewModelScope.launch {
            getFavoritesUseCase.execute(null)
                .onSuccess {
                    getDetail(it)
                }
                .onFailure {
                    when(it){
                        ErrorStates.EMPTY -> getDetail(emptyList())
                        else -> { /*TODO*/ }
                    }
                }
        }
    }

    private fun getDetail(listFavorites: List<MyCharacter>) {
        viewModelScope.launch {
            getCharacterDetailUseCase.execute(characterId)
                .onSuccess {
                    _state.value = state.value.copy(
                        loading = false,
                        character = it.firstOrNull(),
                        isFavorite = listFavorites.contains(it.firstOrNull())
                    )
                }
                .onFailure {
                    /*TODO*/
                }
        }
    }

    fun onFabClicked(character: MyCharacter) {
        _state.value = state.value.copy(
            loading = true
        )
        viewModelScope.launch {
            favorite = if (favorite) {
                removeFromFavoritesUseCase.execute(character)
                    .onSuccess {
                        _state.value = state.value.copy(
                            loading = false,
                            isFavorite = false
                        )
                    }
                    .onFailure { /*TODO*/ }
                false
            } else {
                saveToFavoritesUseCase.execute(character)
                    .onSuccess {
                        _state.value = state.value.copy(
                            loading = false,
                            isFavorite = true
                        )
                    }
                    .onFailure { /*TODO*/ }
                true
            }
        }
    }

    data class State(
        val loading: Boolean = true,
        val character: MyCharacter? = null,
        val isFavorite: Boolean = false
    )
}