package com.applications.toms.usecases.favorites

import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.Result

class SaveFavorite(
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke(character: Result) {
        favoriteRepository.saveCharacter(character)
    }
}