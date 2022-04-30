package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.databinding.FragmentFavoriteBinding
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.Content
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.Loading
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.util.collectFlow
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)

        favoriteAdapter.submitList(emptyList())

        with(binding){
            favoriteRecycler.adapter = favoriteAdapter
            favoriteEditText.doAfterTextChanged { text: Editable? ->
                favoriteViewModel.onSearchCharacter(text)
            }
        }

        lifecycleScope.collectFlow(favoriteViewModel.model,::updateUi)

        lifecycleScope.collectFlow(favoriteViewModel.navigation,::navigateToCharacterDetail)

        return binding.root
    }

    private fun navigateToCharacterDetail(event: Event<MyCharacter?>) {
        event.getContentIfNotHandled()?.let {
            NavHostFragment.findNavController(this).navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it.asDatabaseModel())
            )
        }
    }

    private fun updateUi(model: UiModel){
        when (model){
            is Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is Content -> {
                model.characters.let{
                    if (it.isNullOrEmpty()) addEmptyView() else binding.infoState.visibility = View.GONE
                    favoriteAdapter.submitList(it)
                    binding.loading.visibility = View.GONE
                }
            }
        }
    }

    private fun addEmptyView() {
        with(binding.infoState){
            setInfoState(InfoState.FAV_EMPTY_STATE)
            visibility = View.VISIBLE
        }
    }

}