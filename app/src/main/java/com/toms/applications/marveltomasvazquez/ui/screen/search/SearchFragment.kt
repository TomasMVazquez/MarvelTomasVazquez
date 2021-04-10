package com.toms.applications.marveltomasvazquez.ui.screen.search

import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.databinding.FragmentSearchBinding
import com.toms.applications.marveltomasvazquez.network.Network
import com.toms.applications.marveltomasvazquez.repository.SearchRepository
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.*
import com.toms.applications.marveltomasvazquez.util.getViewModel
import com.toms.applications.marveltomasvazquez.util.hideKeyboard

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var searViewModel: SearchViewModel

    private val searchAdapter by lazy { CharactersRecyclerAdapter(Listener{
        searViewModel.onCharacterClicked(it)
    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)

        val searchRepository = SearchRepository(Network())

        searViewModel = getViewModel { SearchViewModel(searchRepository) }

        binding.searchViewModel = searViewModel

        searchAdapter.submitList(emptyList())

        binding.searchRecycler.adapter = searchAdapter

        searViewModel.model.observe(viewLifecycleOwner, ::updateUi)

        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                searViewModel.onSearchBtnClicked(v.text as Editable)
                hideKeyboard()
            }
            return@setOnEditorActionListener true
        }

        binding.searchTextInput.setEndIconOnClickListener {
            searViewModel.onSearchBtnClicked(binding.searchEditText.text as Editable)
            hideKeyboard()
        }

        searViewModel.navigation.observe(viewLifecycleOwner){ event ->
            event.getContentIfNotHandled()?.let {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
                NavHostFragment.findNavController(this).navigate(action)
            }
        }

        return binding.root
    }

    private fun updateUi(model: UiModel){
        when (model){
            is UiModel.Loading -> {
                binding.loading.visibility = View.VISIBLE
                hideKeyboard()
            }
            is UiModel.Content -> {
                model.characters.let {
                    binding.emptyStateImg.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    searchAdapter.submitList(it)
                    binding.loading.visibility = View.GONE
                }
            }
        }
    }

}