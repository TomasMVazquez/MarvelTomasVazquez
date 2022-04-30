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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.databinding.FragmentSearchBinding
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.UiModel
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.collectFlow
import com.toms.applications.marveltomasvazquez.util.hideKeyboard
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : ScopeFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModel()

    private val searchAdapter by lazy {
        CharactersRecyclerAdapter(
            Listener{ viewModel.onCharacterClicked(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)

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

    private fun navigateToCharacterDetail(event: Event<MyCharacter?>) {
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
                    searchAdapter.submitList(list)
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