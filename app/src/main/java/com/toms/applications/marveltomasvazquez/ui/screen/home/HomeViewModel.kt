package com.toms.applications.marveltomasvazquez.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applications.toms.usecases.characters.GetAllCharacters
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.depormas.utils.ScopedViewModel
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem as Character
import com.toms.applications.marveltomasvazquez.util.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val getAllCharacters: GetAllCharacters, uiDispatcher: CoroutineDispatcher)
    : ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading: UiModel()
        class Content(val characters: MutableList<Character>?): UiModel()
        class ErrorWatcher(val state: InfoState): UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    private val _characters = MutableLiveData<MutableList<Character>>()
    val characters: LiveData<MutableList<Character>> get() = _characters

    private val _navigation = MutableStateFlow<Event<Character?>>(Event(null))
    val navigation: StateFlow<Event<Character?>> get() = _navigation

    init {
        _model.value = Loading
        getCharacterFromUseCase(0)
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

    fun onCharacterClicked(character: Character) {
        _navigation.value = Event(character)
    }

    fun notifyLastVisible(scrolledTo: Int) {
        characters.value?.let { list ->
            val size = list.size
            if (scrolledTo >= (size - getAllCharacters.thresholdSize)){
                _model.value = Loading
                getCharacterFromUseCase(size)
            }
        }
    }

    private fun getCharacterFromUseCase(size: Int){
        launch {
            getAllCharacters.prepare(GetAllCharacters.OkInput(size)).collect { result ->
                result.onSuccess { container ->
                    _characters.addAllItems(container.data.results.map { it.asDatabaseModel() })
                    _characters.notifyObserver()
                }
                result.onFailure { _model.value = ErrorWatcher(InfoState.OTHER) }
            }
        }
    }

    fun onCharactersChanged(list: MutableList<Character>?) {
        if (!list.isNullOrEmpty()) _model.value = Content(list)
    }

}