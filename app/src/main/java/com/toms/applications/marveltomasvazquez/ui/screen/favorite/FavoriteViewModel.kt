package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.favorites.GetFavorites
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavorites: GetFavorites
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            getFavorites.execute(null)
                .onSuccess {
                    _state.value = state.value.copy(
                        loading = false,
                        characters = it
                    )
                }
                .onFailure {
                    _state.value = state.value.copy(
                        loading = false,
                        errorWatcher = when (it) {
                            ErrorStates.EMPTY -> InfoState.FAV_EMPTY_STATE
                            else -> InfoState.OTHER
                        }
                    )
                }
        }
    }

    fun onCharacterClicked(character: MyCharacter) {
        viewModelScope.launch {
            _event.emit(
                Event.GoToDetail(character)
            )
        }
    }

    fun onSearchCharacter(value: Editable?) {
        _state.value = state.value.copy(
            loading = true
        )
        viewModelScope.launch {
            getFavorites.execute(value.toString())
                .onSuccess {
                    _state.value = state.value.copy(
                        loading = false,
                        characters = it
                    )
                }
        }
    }

    data class State(
        val loading: Boolean = true,
        val characters: List<MyCharacter> = emptyList(),
        val errorWatcher: InfoState? = null
    )

    sealed class Event {
        data class GoToDetail(val character: MyCharacter) : Event()
    }
}
