package com.applications.toms.usecases.favorites

import com.applications.toms.data.Either
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.UseCase

class GetFavorites(private val favoriteRepository: FavoriteRepository) :
    UseCase<String?, List<MyCharacter>>() {

    override suspend fun buildUseCase(input: String?): Either<List<MyCharacter>, ErrorStates> =
        if (input.isNullOrEmpty()) favoriteRepository.getCharacters()
        else favoriteRepository.searchCharacters(input)

}
