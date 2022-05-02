package com.applications.toms.usecases.search

import com.applications.toms.data.Either
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.UseCase

class SearchUseCase(val searchRepository: SearchRepository) :
    UseCase<String, List<MyCharacter>>() {

        override suspend fun buildUseCase(input: String): Either<List<MyCharacter>, ErrorStates> =
                searchRepository.getCharacters(input)

}
