package com.toms.applications.marveltomasvazquez.ui.screen.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.usecases.favorites.DeleteFavorite
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.usecases.favorites.SaveFavorite
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.CharacterDatabase
import com.toms.applications.marveltomasvazquez.data.database.RoomDataSource
import com.toms.applications.marveltomasvazquez.databinding.FragmentDetailBinding
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.*
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.collectFlow
import com.toms.applications.marveltomasvazquez.util.getViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)

        val favoriteRepository = FavoriteRepository(RoomDataSource(CharacterDatabase.getInstance(requireContext())))

        viewModel = getViewModel { DetailViewModel(
            GetFavorites(favoriteRepository),
            SaveFavorite(favoriteRepository), DeleteFavorite(favoriteRepository),
            args.character) }

        with(binding){
            detailViewModel = detailViewModel
            character = args.character
            characterDetails.setAttributions(args.character.asDomainModel())
            with(toolbar){
                setNavigationIcon(R.drawable.ic_baseline_arrow_back)
                setNavigationOnClickListener { findNavController().popBackStack() }
            }
        }

        lifecycleScope.collectFlow(viewModel.model,::updateUI)
        
        return binding.root
    }

    private fun updateUI(model: UiModel?) {
        binding.loading.visibility = if (model == Loading) View.VISIBLE else View.GONE
        when(model){
            Favorite -> binding.fabFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_favorite_24))
            NotFavorite -> binding.fabFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_favorite_border))
        }
    }

}