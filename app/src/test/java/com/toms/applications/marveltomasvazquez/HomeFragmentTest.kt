package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.MyCharacter
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.testshared.mockCharacter
import com.applications.toms.usecases.characters.GetAllCharacters
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    private lateinit var homeViewModel: HomeViewModel
    private val fakeRemoteRepository = FakeRemoteRepository()
    private val fakeLocalRepository = FakeLocalRepository()
    private val characterRepository = CharactersRepository(fakeRemoteRepository,fakeLocalRepository)
    private val getAllCharacters = GetAllCharacters(characterRepository)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        homeViewModel = HomeViewModel(getAllCharacters, Dispatchers.Unconfined)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `update UI when init`() = coroutineTestRule.testDispatcher.runBlockingTest {
        homeViewModel.model.test {
            assertEquals(Loading,awaitItem())
            assertEquals(Content(listOfMocks.toMutableList()),awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `navigation to detail when clicked on recycler item`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val character = mockCharacter

        homeViewModel.onCharacterClicked(character)

        homeViewModel.navigation.test {
            assertEquals(character,awaitItem().peekContent())
            cancelAndConsumeRemainingEvents()
        }
    }

}