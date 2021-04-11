package com.toms.applications.marveltomasvazquez.ui.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.network.*
import com.toms.applications.marveltomasvazquez.databinding.FragmentHomeBinding
import com.toms.applications.marveltomasvazquez.repository.CharactersRepository
import com.toms.applications.marveltomasvazquez.ui.adapters.CharactersRecyclerAdapter
import com.toms.applications.marveltomasvazquez.ui.adapters.Listener
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.getViewModel

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

        val charactersRepository = CharactersRepository(Network())

        homeViewModel = getViewModel { HomeViewModel(charactersRepository) }

        characterAdapter.submitList(emptyList())

        binding.charactersRecycler.adapter = characterAdapter

        homeViewModel.model.observe(viewLifecycleOwner, ::updateUi)

        // To call Network Characters when we are reaching the end of last updated list
        val layoutManager = binding.charactersRecycler.layoutManager as GridLayoutManager

        binding.charactersRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               homeViewModel.notifyLastVisible(layoutManager.findLastVisibleItemPosition())
            }
        })

        homeViewModel.characters.observe(viewLifecycleOwner) { homeViewModel.onCharactersChanged(it) }

        homeViewModel.navigation.observe(viewLifecycleOwner){ event ->
            event.getContentIfNotHandled()?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                NavHostFragment.findNavController(this).navigate(action)
            }
        }

        return binding.root
    }

    private fun updateUi(model: UiModel){
        when (model){
            is Loading ->  binding.progress.visibility = View.VISIBLE
            is Content -> {
                model.characters?.let {
                    characterAdapter.submitList(it)
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}