package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.databinding.FragmentFavoriteBinding
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.util.collectEvent
import com.toms.applications.marveltomasvazquez.util.collectState
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : ScopeFragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private val favoriteAdapter by lazy {
        CharactersRecyclerAdapter(
            Listener { favoriteViewModel.onCharacterClicked(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        favoriteAdapter.submitList(emptyList())

        with(binding) {
            favoriteRecycler.adapter = favoriteAdapter
            favoriteEditText.doAfterTextChanged { text: Editable? ->
                favoriteViewModel.onSearchCharacter(text)
            }
        }

        collectState(favoriteViewModel.state, ::renderState)
        collectEvent(favoriteViewModel.event, ::launchEvent)

        return binding.root
    }

    private fun launchEvent(event: FavoriteViewModel.Event) {
        when (event) {
            is FavoriteViewModel.Event.GoToDetail -> {
                findNavController().navigate(
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                        event.character.asDatabaseModel()
                    )
                )
            }
        }
    }

    private fun renderState(state: FavoriteViewModel.State) {
        binding.loading.visibility = if (state.loading) View.VISIBLE else View.GONE
        when {
            state.characters.isNotEmpty() -> {
                if (state.characters.isNullOrEmpty()) addEmptyView() else binding.infoState.visibility =
                    View.GONE
                favoriteAdapter.submitList(state.characters)
                binding.loading.visibility = View.GONE
            }
            state.errorWatcher != null -> {
                if (state.errorWatcher == InfoState.FAV_EMPTY_STATE) addEmptyView()
            }
        }
    }

    private fun addEmptyView() {
        with(binding.infoState) {
            setInfoState(InfoState.FAV_EMPTY_STATE)
            visibility = View.VISIBLE
        }
    }

}