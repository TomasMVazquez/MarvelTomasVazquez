package com.toms.applications.marveltomasvazquez.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applications.toms.usecases.GetAllCharacters
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem as Character
import com.toms.applications.marveltomasvazquez.util.*
import com.toms.applications.marveltomasvazquez.util.Scope.*
import kotlinx.coroutines.launch

class HomeViewModel(private val getAllCharacters: GetAllCharacters)
    : ViewModel(), Scope by ImplementJob() {

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
            //TODO CONECTION INTERNET
            getAllCharacters.invoke(0,0).data.results.map {
                _characters.addNewItem(it.asDatabaseModel())
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
            getAllCharacters.invoke(lastVisible,characters.value?.size ?: 0)
                .data
                .results
                .map {
                    _characters.addNewItem(it.asDatabaseModel())
                }
            _characters.notifyObserver()
        }
    }

    fun onCharactersChanged(list: MutableList<Character>?) {
        if (!list.isNullOrEmpty()) _model.value = UiModel.Content(list)
    }

}