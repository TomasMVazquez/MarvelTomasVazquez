package com.toms.applications.marveltomasvazquez.ui.screen.search

import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.applications.toms.data.repository.SearchRepository
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.applications.toms.domain.Result as Character
import com.toms.applications.marveltomasvazquez.databinding.FragmentSearchBinding
import com.toms.applications.marveltomasvazquez.data.server.ServerDataSource
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeFragmentDirections
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.collectFlow
import com.toms.applications.marveltomasvazquez.util.getViewModel
import com.toms.applications.marveltomasvazquez.util.hideKeyboard

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: SearchViewModel

    private val searchAdapter by lazy {
        CharactersRecyclerAdapter(
            Listener{ viewModel.onCharacterClicked(it.asDomainModel()) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)

        val searchRepository = SearchRepository(ServerDataSource())

        viewModel = getViewModel { SearchViewModel(searchRepository) }

        searchAdapter.submitList(emptyList())

        with(binding){
            searchViewModel = viewModel
            searchRecycler.adapter = searchAdapter
            searchEditText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.action == KeyEvent.ACTION_DOWN
                    && event.keyCode == KeyEvent.KEYCODE_ENTER){
                    viewModel.onSearchBtnClicked(v.text as Editable)
                    hideKeyboard()
                }
                return@setOnEditorActionListener true
            }
            /* favoriteEditText.doAfterTextChanged { text: Editable? ->
                favoriteViewModel.onSearchCharacter(text)
            }*/
            searchEditText.doAfterTextChanged { text ->
                imgSearch.visibility = if (text.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }

        with(viewModel){
            lifecycleScope.collectFlow(model,::updateUi)
            lifecycleScope.collectFlow(navigation,::navigateToCharacterDetail)

        }

        return binding.root
    }

    private fun navigateToCharacterDetail(event: Event<Character?>) {
        event.getContentIfNotHandled()?.let {
            NavHostFragment.findNavController(this).navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.asDatabaseModel())
            )
        }
    }

    private fun updateUi(model: UiModel){
        when (model){
            is UiModel.Loading -> {
                binding.loading.visibility = View.VISIBLE
                hideKeyboard()
            }
            is UiModel.Content -> {
                model.characters.let { list ->
                    binding.infoState.visibility = View.GONE
                    binding.searchRecycler.visibility = View.VISIBLE
                    searchAdapter.submitList(list.map { it.asDatabaseModel() })
                    binding.loading.visibility = View.GONE
                }
            }
            is UiModel.ErrorWatcher -> {
                binding.loading.visibility = View.GONE
                binding.searchRecycler.visibility = View.GONE
                binding.infoState.visibility = View.VISIBLE
                binding.infoState.setInfoState(model.infoState)
            }
        }
    }

}