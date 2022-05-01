package com.toms.applications.marveltomasvazquez.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.characters.GetAllCharacters
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.util.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllCharacters: GetAllCharacters
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    init {
        getCharacterFromUseCase(0)
    }

    fun onCharacterClicked(character: MyCharacter) {
        viewModelScope.launch {
            _event.emit(
                Event.GoToDetail(character)
            )
        }
    }

    fun notifyLastVisible(scrolledTo: Int) {
        _state.value.characters.let { list ->
            val size = list.size
            if (scrolledTo >= (size - CharactersRepository.THRESHOLD_SIZE)) {
                _state.value = state.value.copy(
                    loading = true,
                )
                getCharacterFromUseCase(size)
            }
        }
    }

    private fun getCharacterFromUseCase(size: Int) {
        viewModelScope.launch {
            getAllCharacters.execute(GetAllCharacters.OkInput(size))
                .onSuccess { list ->
                    if (!list.isNullOrEmpty()) {
                        _state.value = state.value.copy(
                            loading = false,
                            characters = list
                        )
                    } else {
                        _state.value = state.value.copy(
                            loading = false,
                            errorWatcher = InfoState.NETWORK_ERROR
                        )
                    }
                }
                .onFailure {
                    _state.value = state.value.copy(
                        loading = false,
                        errorWatcher = InfoState.OTHER
                    )
                }
        }
    }

    fun onCharactersChanged(list: MutableList<MyCharacter>?) {
        if (!list.isNullOrEmpty())
            _state.value = state.value.copy(
                loading = false,
                characters = list
            )
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