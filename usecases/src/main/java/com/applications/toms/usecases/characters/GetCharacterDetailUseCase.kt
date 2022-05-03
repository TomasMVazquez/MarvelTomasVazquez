package com.applications.toms.usecases.characters

import com.applications.toms.data.Either
import com.applications.toms.data.repository.CharacterDetailRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.UseCase

class GetCharacterDetailUseCase(
    private val characterDetailRepository: CharacterDetailRepository
): UseCase<String, List<MyCharacter>>() {

    override suspend fun buildUseCase(input: String): Either<List<MyCharacter>, ErrorStates> =
        characterDetailRepository.getCharacterDetail(input)

}
