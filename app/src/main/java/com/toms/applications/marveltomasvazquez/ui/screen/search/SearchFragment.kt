package com.toms.applications.marveltomasvazquez.ui.screen.search

import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.databinding.FragmentSearchBinding
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.util.collectEvent
import com.toms.applications.marveltomasvazquez.util.collectState
import com.toms.applications.marveltomasvazquez.util.hideKeyboard
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : ScopeFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModel()

    private val searchAdapter by lazy {
        CharactersRecyclerAdapter(
            Listener { viewModel.onCharacterClicked(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        searchAdapter.submitList(emptyList())

        with(binding) {
            searchViewModel = viewModel
            searchRecycler.adapter = searchAdapter
            searchEditText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.action == KeyEvent.ACTION_DOWN
                    && event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.onSearchBtnClicked(v.text as Editable)
                    hideKeyboard()
                }
                return@setOnEditorActionListener true
            }

            searchEditText.doAfterTextChanged { text ->
                imgSearch.visibility = if (text.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }

        collectState(viewModel.state, ::renderState)
        collectEvent(viewModel.event, ::launchEvent)

        return binding.root
    }

    private fun launchEvent(event: SearchViewModel.Event) {
        when (event) {
            is SearchViewModel.Event.GoToDetail -> {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                        event.character.id.toString()
                    )
                )
            }
        }
    }

    private fun renderState(state: SearchViewModel.State) {
        when {
            state.loading -> {
                binding.loading.visibility = View.VISIBLE
                hideKeyboard()
            }
            !state.characters.isNullOrEmpty() -> {
                binding.infoState.visibility = View.GONE
                binding.searchRecycler.visibility = View.VISIBLE
                searchAdapter.submitList(state.characters)
                binding.loading.visibility = View.GONE
            }
            state.errorWatcher != null -> {
                binding.loading.visibility = View.GONE
                binding.searchRecycler.visibility = View.GONE
                binding.infoState.visibility = View.VISIBLE
                binding.infoState.setInfoState(state.errorWatcher)
            }
        }
    }

}