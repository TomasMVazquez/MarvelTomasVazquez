package com.applications.toms.usecases.characters

import com.applications.toms.data.*
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.data.repository.CharactersRepository.Companion.NETWORK_LIMIT_CHARACTERS
import com.applications.toms.data.repository.CharactersRepository.Companion.THRESHOLD_SIZE
import com.applications.toms.domain.CharactersContainer
import com.applications.toms.usecases.FlowUseCase
import com.applications.toms.usecases.characters.GetAllCharacters.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetAllCharacters(private val charactersRepository: CharactersRepository):
    FlowUseCase<OkInput, Either<CharactersContainer, String>>(){

    var thresholdSize = THRESHOLD_SIZE
    var networkLimit = NETWORK_LIMIT_CHARACTERS

    data class OkInput(
        val offset: Int
    )

    override fun prepareFlow(input: OkInput): Flow<Either<CharactersContainer, String>> = flow {
        charactersRepository.getCharacters(input.offset).collect { result ->
            result.onSuccess { emit(eitherSuccess(it)) }
            result.onFailure { emit(eitherFailure(it)) }
        }
    }

}