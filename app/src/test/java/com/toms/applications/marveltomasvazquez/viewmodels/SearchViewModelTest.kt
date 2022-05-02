package com.toms.applications.marveltomasvazquez.viewmodels

import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.data.repository.SearchRepository
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()
    @Mock
    private val repository = SearchRepository(fakeRemoteRepository)

    private val viewModel: SearchViewModel by lazy { SearchViewModel(repository) }


}