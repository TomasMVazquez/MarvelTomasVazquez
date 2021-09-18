package com.toms.applications.marveltomasvazquez.ui.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.usecases.characters.GetAllCharacters
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem
import com.toms.applications.marveltomasvazquez.databinding.FragmentHomeBinding
import com.toms.applications.marveltomasvazquez.data.server.ServerDataSource
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.collectFlow
import com.toms.applications.marveltomasvazquez.util.getViewModel
import com.toms.applications.marveltomasvazquez.util.lastVisibleEvents

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private val characterAdapter by lazy { CharactersRecyclerAdapter(Listener{
        homeViewModel.onCharacterClicked(it)
    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)

        val getAllCharacters = GetAllCharacters(CharactersRepository(ServerDataSource()))

        homeViewModel = getViewModel { HomeViewModel(getAllCharacters) }

        characterAdapter.submitList(emptyList())

        binding.charactersRecycler.adapter = characterAdapter

        lifecycleScope.collectFlow(homeViewModel.model, ::updateUi)

        lifecycleScope.collectFlow(homeViewModel.navigation, ::navigateToCharacterDetail)

        homeViewModel.characters.observe(viewLifecycleOwner) { homeViewModel.onCharactersChanged(it) }

        lifecycleScope.collectFlow(binding.charactersRecycler.lastVisibleEvents) {
            //TODO SAVE LAST ITEM VIEW WHEN RETURN
            homeViewModel.notifyLastVisible(it)
        }

        return binding.root
    }

    private fun navigateToCharacterDetail(event: Event<CharacterDatabaseItem?>) {
        event.getContentIfNotHandled()?.let {
            NavHostFragment.findNavController(this).navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            )
        }
    }

    private fun updateUi(model: UiModel){
        when (model){
            is Loading ->  onShowLoading(true)
            is Content -> {
                model.characters?.let {
                    characterAdapter.submitList(it)
                    onShowInfoState(false)
                }
            }
            is ErrorWatcher -> {
                onShowInfoState(true)
                binding.infoState.setInfoState(model.state)
            }
        }
    }

    private fun onShowLoading(show: Boolean){
        binding.charactersRecycler.suppressLayout(show)
        binding.progress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun onShowInfoState(show: Boolean){
        onShowLoading(false)
        binding.charactersRecycler.visibility = if (show) View.GONE else View.VISIBLE
        binding.infoState.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}