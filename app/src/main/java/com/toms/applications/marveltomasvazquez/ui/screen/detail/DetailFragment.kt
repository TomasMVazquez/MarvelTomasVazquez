package com.toms.applications.marveltomasvazquez.ui.screen.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.database.CharacterDatabase
import com.toms.applications.marveltomasvazquez.database.RoomDataSource
import com.toms.applications.marveltomasvazquez.databinding.FragmentDetailBinding
import com.toms.applications.marveltomasvazquez.domain.Character
import com.toms.applications.marveltomasvazquez.repository.FavoriteRepository
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.*
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.getViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)

        val favoriteRepository = FavoriteRepository(RoomDataSource(CharacterDatabase.getInstance(requireContext())))

        detailViewModel = getViewModel { DetailViewModel(favoriteRepository, args.character) }

        binding.detailViewModel = detailViewModel

        binding.character = args.character

        binding.characterDetails.setAttributions(args.character)

        detailViewModel.model.observe(viewLifecycleOwner,::updateUI)

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        
        return binding.root
    }

    private fun updateUI(model: UiModel?) {
        if (model == Loading) binding.loading.visibility = View.VISIBLE else binding.loading.visibility = View.GONE
        when(model){
            Favorite -> binding.fabFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_favorite_24))
            NotFavorite -> binding.fabFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_favorite_border))
        }
    }

}