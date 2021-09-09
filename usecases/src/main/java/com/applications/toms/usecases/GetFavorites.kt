package com.applications.toms.usecases

import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.Result

class GetFavorites(
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke(search: String?): List<Result> {
        return if (search.isNullOrEmpty()){
            favoriteRepository.getCharacters()
        }else{
            favoriteRepository.searchCharacters(search)
        }
    }
}