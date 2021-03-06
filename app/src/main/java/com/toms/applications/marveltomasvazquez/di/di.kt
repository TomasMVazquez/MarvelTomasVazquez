package com.toms.applications.marveltomasvazquez.di

import android.app.Application
import com.applications.toms.data.repository.CharacterDetailRepository
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.usecases.characters.GetAllCharactersUseCase
import com.applications.toms.usecases.characters.GetCharacterDetailUseCase
import com.applications.toms.usecases.favorites.GetFavoritesUseCase
import com.applications.toms.usecases.favorites.RemoveFromFavoritesUseCase
import com.applications.toms.usecases.favorites.SaveToFavoritesUseCase
import com.applications.toms.usecases.search.SearchUseCase
import com.toms.applications.marveltomasvazquez.data.database.CharacterDatabase
import com.toms.applications.marveltomasvazquez.data.database.RoomDataSource
import com.toms.applications.marveltomasvazquez.data.server.ServerDataSource
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailFragment
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteFragment
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeFragment
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchFragment
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { CharacterDatabase.getInstance(get()) }

    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { ServerDataSource() }

    single<CoroutineDispatcher> { Dispatchers.Main }
}

private val dataModule = module {
    factory { CharactersRepository(get(), get()) }
    factory { CharacterDetailRepository(get()) }
    factory { FavoriteRepository(get()) }
    factory { SearchRepository(get()) }
}

private val scopesModule = module {
    scope(named<HomeFragment>()) {
        viewModel { HomeViewModel(get()) }
        scoped { GetAllCharactersUseCase(get()) }
    }

    scope(named<FavoriteFragment>()) {
        viewModel { FavoriteViewModel(get()) }
        scoped { GetFavoritesUseCase(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (characterId: String) ->
            DetailViewModel(
                get(),
                get(),
                get(),
                get(),
                characterId
            )
        }
        scoped { GetCharacterDetailUseCase(get()) }
        scoped { GetFavoritesUseCase(get()) }
        scoped { SaveToFavoritesUseCase(get()) }
        scoped { RemoveFromFavoritesUseCase(get()) }
    }

    scope(named<SearchFragment>()) {
        viewModel { SearchViewModel(get()) }
        scoped { SearchUseCase(get()) }
    }
}