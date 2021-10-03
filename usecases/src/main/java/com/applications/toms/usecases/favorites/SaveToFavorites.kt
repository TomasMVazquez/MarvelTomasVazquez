package com.applications.toms.usecases.favorites

import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.MyCharacter

class SaveToFavorites(
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke(character: MyCharacter) {
        favoriteRepository.addToFavorites(character)
    }
}