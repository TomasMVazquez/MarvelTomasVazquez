package com.applications.toms.usecases

import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.CharactersContainer

class GetAllCharacters(
    private val charactersRepository: CharactersRepository
) {

    suspend fun invoke(lastVisible: Int,offset: Int): CharactersContainer {
        return charactersRepository.getCharacters(lastVisible,offset)
    }

}