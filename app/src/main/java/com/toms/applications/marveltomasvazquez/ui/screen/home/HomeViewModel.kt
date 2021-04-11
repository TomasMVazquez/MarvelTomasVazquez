package com.toms.applications.marveltomasvazquez.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toms.applications.marveltomasvazquez.domain.Character
import com.toms.applications.marveltomasvazquez.network.model.asDomainModel
import com.toms.applications.marveltomasvazquez.repository.CharactersRepository
import com.toms.applications.marveltomasvazquez.util.*
import com.toms.applications.marveltomasvazquez.util.Scope.*
import kotlinx.coroutines.launch

class HomeViewModel(private val charactersRepository: CharactersRepository) : ViewModel(), Scope by ImplementJob() {

    sealed class UiModel {
        object Loading: UiModel()
        class Content(val characters: MutableList<Character>?): UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = UiModel.Loading
            return _model
        }

    private val _characters = MutableLiveData<MutableList<Character>>()
    val characters: LiveData<MutableList<Character>>
        get() = _characters

    private val _navigation = MutableLiveData<Event<Character>>()
    val navigation: LiveData<Event<Character>> get() = _navigation

    init {
        initScope()
        _model.value = UiModel.Loading
        launch {
            charactersRepository.getCharacters(0).data.results.asDomainModel().map {
                _characters.addNewItem(it)
            }
            _characters.notifyObserver()
        }
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

    fun onCharacterClicked(character: Character) {
        _navigation.value = Event(character)
    }

    fun notifyLastVisible(lastVisible: Int) {
        launch {
            charactersRepository
                .checkRequireGetMoreCharacters(lastVisible,characters.value?.size ?: 0)
                ?.data
                ?.results
                ?.asDomainModel()?.map {
                    _characters.addNewItem(it)
                }
            _characters.notifyObserver()
        }
    }

    fun onCharactersChanged(list: MutableList<Character>?) {
        if (!list.isNullOrEmpty()) _model.value = UiModel.Content(list)
    }

}