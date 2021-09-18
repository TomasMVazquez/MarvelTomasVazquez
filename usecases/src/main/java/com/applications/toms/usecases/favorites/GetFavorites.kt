package com.applications.toms.usecases.favorites

import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.Result
import com.applications.toms.data.Either
import com.applications.toms.usecases.FlowUseCase
import com.applications.toms.data.eitherSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavorites(private val favoriteRepository: FavoriteRepository):
    FlowUseCase<String?, Either<Flow<List<Result>>, String>>(){

    override fun prepareFlow(input: String?) = flow {
        if (input.isNullOrEmpty()){
            emit(eitherSuccess(favoriteRepository.getCharacters()))
        }else{
            emit(eitherSuccess(favoriteRepository.searchCharacters(input)))
        }
    }
}