package com.applications.toms.usecases.favorites

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.UseCase

class RemoveFromFavorites(
    private val favoriteRepository: FavoriteRepository
): UseCase<MyCharacter, EitherState>() {

    override suspend fun buildUseCase(input: MyCharacter): Either<EitherState, ErrorStates> =
        favoriteRepository.removeFromFavorites(input)
}
