package com.toms.applications.marveltomasvazquez.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.databinding.FragmentHomeBinding
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.collectFlow
import com.toms.applications.marveltomasvazquez.util.lastVisibleEvents
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ScopeFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    private val characterAdapter by lazy { CharactersRecyclerAdapter(Listener{
        homeViewModel.onCharacterClicked(it)
    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)

        characterAdapter.submitList(emptyList())

        binding.charactersRecycler.adapter = characterAdapter

        lifecycleScope.collectFlow(homeViewModel.model, ::updateUi)

        lifecycleScope.collectFlow(homeViewModel.navigation, ::navigateToCharacterDetail)

        homeViewModel.characters.observe(viewLifecycleOwner) { homeViewModel.onCharactersChanged(it) }

        lifecycleScope.collectFlow(binding.charactersRecycler.lastVisibleEvents) {
            homeViewModel.notifyLastVisible(it)
        }

        return binding.root
    }

    private fun navigateToCharacterDetail(event: Event<MyCharacter?>) {
        event.getContentIfNotHandled()?.let {
            NavHostFragment.findNavController(this).navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.asDatabaseModel())
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