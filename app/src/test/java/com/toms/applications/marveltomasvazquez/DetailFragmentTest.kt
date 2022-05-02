package com.toms.applications.marveltomasvazquez

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DetailFragmentTest {
/*
    private lateinit var detailViewModel: DetailViewModel
    private val fakeLocalRepository = FakeLocalRepository()
    private val favoriteRepository = FavoriteRepository(fakeLocalRepository)
    private val getFavoriteRepository = GetFavorites(favoriteRepository)
    private val saveFavorites = SaveToFavorites(favoriteRepository)
    private val removeFavorite = RemoveFromFavorites(favoriteRepository)


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        detailViewModel = DetailViewModel(getFavoriteRepository,saveFavorites,removeFavorite,
            mockCharacter,Dispatchers.Unconfined)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `update UI when init`() = coroutineTestRule.testDispatcher.runBlockingTest {
        detailViewModel.model.test {
            assertEquals(Loading,awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onFabClicked update UI icon`() = coroutineTestRule.testDispatcher.runBlockingTest {

        detailViewModel.onFabClicked(mockCharacter)

        detailViewModel.model.test {
            assertEquals(Loading,awaitItem())
            assertEquals(Favorite,awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }*/
}