package com.applications.toms.usecases.characters

import com.applications.toms.data.*
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.UseCase
import com.applications.toms.usecases.characters.GetAllCharacters.OkInput

class GetAllCharacters(private val charactersRepository: CharactersRepository) :
    UseCase<OkInput, List<MyCharacter>>() {

    override suspend fun buildUseCase(input: OkInput): Either<List<MyCharacter>, ErrorStates> =
        charactersRepository.getCharacters(input.offset)
            .onSuccess { eitherSuccess(it) }
            .onFailure { eitherFailure(it) }

    data class OkInput(
        val offset: Int
    )
}