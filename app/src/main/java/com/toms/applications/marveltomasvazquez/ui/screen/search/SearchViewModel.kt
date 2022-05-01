package com.toms.applications.marveltomasvazquez.ui.screen.search

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    fun onSearchBtnClicked(value: Editable) {
        _state.value = state.value.copy(
            loading = true
        )

        viewModelScope.launch {
            searchRepository.getCharacters(value.toString())
                .onSuccess {
                    if (it.isEmpty())
                        _state.value = state.value.copy(
                            loading = false,
                            errorWatcher = InfoState.SEARCH_EMPTY
                        )
                    else
                        _state.value = state.value.copy(
                            loading = false,
                            characters = it
                        )
                }
                .onFailure {
                    _state.value = state.value.copy(
                        loading = false,
                        errorWatcher = InfoState.OTHER
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

    data class State(
        val loading: Boolean = false,
        val characters: List<MyCharacter> = emptyList(),
        val errorWatcher: InfoState? = null
    )

    sealed class Event {
        data class GoToDetail(val character: MyCharacter) : Event()
    }

}
