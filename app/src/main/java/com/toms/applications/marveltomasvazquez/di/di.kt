package com.toms.applications.marveltomasvazquez.di

import android.app.Application
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.MyCharacter
import com.applications.toms.usecases.characters.GetAllCharacters
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.usecases.favorites.RemoveFromFavorites
import com.applications.toms.usecases.favorites.SaveToFavorites
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
    factory { CharactersRepository(get(),get()) }
    factory { FavoriteRepository(get()) }
    factory { SearchRepository(get()) }
}

private val scopesModule = module {
    scope(named<HomeFragment>()) {
        viewModel { HomeViewModel(get(),get()) }
        scoped { GetAllCharacters(get()) }
    }

    scope(named<FavoriteFragment>()) {
        viewModel { FavoriteViewModel(get(),get()) }
        scoped { GetFavorites(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (character: MyCharacter) -> DetailViewModel(get(),get(),get(),character,get()) }
        scoped { GetFavorites(get()) }
        scoped { SaveToFavorites(get()) }
        scoped { RemoveFromFavorites(get()) }
    }

    scope(named<SearchFragment>()) {
        viewModel { SearchViewModel(get(),get()) }
    }
}