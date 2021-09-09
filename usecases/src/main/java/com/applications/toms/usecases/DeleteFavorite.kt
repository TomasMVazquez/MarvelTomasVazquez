package com.applications.toms.usecases

import com.applications.toms.data.repository.FavoriteRepository

class DeleteFavorite (
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke(characterId: Long) {
        favoriteRepository.deleteCharacter(characterId)
    }
}