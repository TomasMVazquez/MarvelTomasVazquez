package com.toms.applications.marveltomasvazquez.ui.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.databinding.FragmentDetailBinding
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.collectFlow
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : ScopeFragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(args.character.asDomainModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)

        with(binding){
            detailViewModel = viewModel
            character = args.character.asDomainModel()
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