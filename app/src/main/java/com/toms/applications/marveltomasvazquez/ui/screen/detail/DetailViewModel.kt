package com.toms.applications.marveltomasvazquez.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.characters.GetCharacterDetailUseCase
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.usecases.favorites.RemoveFromFavorites
import com.applications.toms.usecases.favorites.SaveToFavorites
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getFavorites: GetFavorites,
    private val saveToFavorites: SaveToFavorites,
    private val removeFromFavorites: RemoveFromFavorites,
    private val characterId: String
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private var favorite: Boolean = false

    fun getData() {
        viewModelScope.launch {
            getFavorites.execute(null)
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
                removeFromFavorites.execute(character)
                    .onSuccess {
                        _state.value = state.value.copy(
                            loading = false,
                            isFavorite = false
                        )
                    }
                    .onFailure { /*TODO*/ }
                false
            } else {
                saveToFavorites.execute(character)
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