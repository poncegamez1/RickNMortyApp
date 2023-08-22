package com.poncegamez.ricknmortyapp.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.poncegamez.ricknmortyapp.R
import com.poncegamez.ricknmortyapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var listAdapter = ListAdapter()
    private val viewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        setUpAdapter()
        setUpRecyclerView()
        setupSearchView()
        addSearchSubscriptions()
    }

    private fun setUpAdapter() {
        listAdapter.setOnItemClickListener { character ->
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailFragment(characterId = character.id)
            )
        }
    }

    private fun setUpRecyclerView() {
        binding.listRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                header = ListLoadStateAdapter{listAdapter.retry()},
                footer = ListLoadStateAdapter{listAdapter.retry()}
            )
            setHasFixedSize(true)
        }
    }

    private fun addSearchSubscriptions() {
        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.searchListFlow.collectLatest { pagingData ->
                    listAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.listSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.listRecyclerView.scrollToPosition(0)
                viewModel.searchCharacters(query)
                Log.i("test1", query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchCharacters(newText)
                Log.i("test2", newText)
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}