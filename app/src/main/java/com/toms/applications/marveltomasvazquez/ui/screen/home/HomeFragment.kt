package com.toms.applications.marveltomasvazquez.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.databinding.FragmentHomeBinding
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.util.collectEvent
import com.toms.applications.marveltomasvazquez.util.collectFlow
import com.toms.applications.marveltomasvazquez.util.collectState
import com.toms.applications.marveltomasvazquez.util.lastVisibleEvents
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ScopeFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    private val characterAdapter by lazy {
        CharactersRecyclerAdapter(Listener {
            homeViewModel.onCharacterClicked(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        characterAdapter.submitList(emptyList())

        binding.charactersRecycler.adapter = characterAdapter

        collectState(homeViewModel.state, ::renderState)
        collectEvent(homeViewModel.event, ::launchEvent)

        lifecycleScope.collectFlow(binding.charactersRecycler.lastVisibleEvents) {
            homeViewModel.notifyLastVisible(it)
        }

        return binding.root
    }

    private fun launchEvent(event: HomeViewModel.Event) {
        when (event) {
            is HomeViewModel.Event.GoToDetail -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        event.character.id.toString()
                    )
                )
            }
        }
    }

    private fun renderState(state: HomeViewModel.State) {
        when {
            state.loading -> onShowLoading(true)
            state.characters.isNotEmpty() -> {
                onShowInfoState(false)
                characterAdapter.submitList(state.characters)
            }
            state.errorWatcher != null -> {
                onShowInfoState(true)
                binding.infoState.setInfoState(state.errorWatcher)
            }
        }
    }

    private fun onShowLoading(show: Boolean) {
        binding.charactersRecycler.suppressLayout(show)
        binding.progress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun onShowInfoState(show: Boolean) {
        onShowLoading(false)
        binding.charactersRecycler.visibility = if (show) View.GONE else View.VISIBLE
        binding.infoState.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}