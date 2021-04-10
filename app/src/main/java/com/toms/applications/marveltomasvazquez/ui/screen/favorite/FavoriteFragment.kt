package com.toms.applications.marveltomasvazquez.ui.screen.favorite

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.database.CharacterDatabase
import com.toms.applications.marveltomasvazquez.database.RoomDataSource
import com.toms.applications.marveltomasvazquez.databinding.FragmentFavoriteBinding
import com.toms.applications.marveltomasvazquez.repository.FavoriteRepository
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.*
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel
import com.toms.applications.marveltomasvazquez.util.getViewModel
import com.toms.applications.marveltomasvazquez.util.hideKeyboard


class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoriteViewModel: FavoriteViewModel

    private val favoriteAdapter by lazy { CharactersRecyclerAdapter(Listener{
        favoriteViewModel.onCharacterClicked(it)
    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)

        val favoriteRepository = FavoriteRepository(RoomDataSource(CharacterDatabase.getInstance(requireContext())))

        favoriteViewModel = getViewModel { FavoriteViewModel(favoriteRepository) }

        favoriteAdapter.submitList(emptyList())

        binding.favoriteRecycler.adapter = favoriteAdapter

        favoriteViewModel.model.observe(viewLifecycleOwner, ::updateUi)

        binding.favoriteEditText.doAfterTextChanged { text: Editable? ->
            favoriteViewModel.searchCharacter(text)
        }

        favoriteViewModel.navigation.observe(viewLifecycleOwner){ event ->
            event.getContentIfNotHandled()?.let {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it)
                NavHostFragment.findNavController(this).navigate(action)
            }
        }

        return binding.root
    }

    private fun updateUi(model: UiModel){
        when (model){
            is Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is Content -> {
                model.characters.observe(viewLifecycleOwner){
                    it.let {
                        binding.emtpyState.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
                        favoriteAdapter.submitList(it)
                        binding.loading.visibility = View.GONE
                    }
                }
            }
        }
    }

}