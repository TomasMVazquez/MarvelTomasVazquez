package com.toms.applications.marveltomasvazquez.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.usecases.favorites.RemoveFromFavorites
import com.applications.toms.usecases.favorites.SaveToFavorites
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getFavorites: GetFavorites,
    private val saveToFavorites: SaveToFavorites,
    private val removeFromFavorites: RemoveFromFavorites,
    character: MyCharacter
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private lateinit var databaseItems: List<MyCharacter>

    private var favorite: Boolean = false

    init {
        viewModelScope.launch {
            getFavorites.execute(null)
                .onSuccess {
                    databaseItems = it
                    favorite = databaseItems.contains(character)
                    _state.value = state.value.copy(
                        loading = false,
                        isFavorite = favorite
                    )
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
        val loading: Boolean = false,
        val isFavorite: Boolean = false
    )
}