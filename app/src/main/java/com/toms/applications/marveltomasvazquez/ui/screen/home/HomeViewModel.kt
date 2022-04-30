package com.toms.applications.marveltomasvazquez.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.characters.GetAllCharacters
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel.*
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
        class Content(val characters: MutableList<MyCharacter>?): UiModel()
        class ErrorWatcher(val state: InfoState): UiModel()
    }

    private val _model = MutableStateFlow<UiModel>(Loading)
    val model: StateFlow<UiModel> get() = _model

    private val _characters = MutableLiveData<MutableList<MyCharacter>>()
    val characters: LiveData<MutableList<MyCharacter>> get() = _characters

    private val _navigation = MutableStateFlow<Event<MyCharacter?>>(Event(null))
    val navigation: StateFlow<Event<MyCharacter?>> get() = _navigation

    init {
        _model.value = Loading
        getCharacterFromUseCase(0)
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

    fun onCharacterClicked(character: MyCharacter) {
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
                result.onSuccess { list ->
                    list.asSequence().iterator().forEach {
                        if (size > 0)
                            _characters.addNewItemAt(size,it)
                        else
                            _characters.addNewItem(it)
                        _characters.notifyObserver()
                    }
                }
                result.onFailure { _model.value = ErrorWatcher(InfoState.OTHER) }
            }
        }
    }

    fun onCharactersChanged(list: MutableList<MyCharacter>?) {
        if (!list.isNullOrEmpty()) _model.value = Content(list)
    }

}