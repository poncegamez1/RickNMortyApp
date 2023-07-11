package com.poncegamez.ricknmortyapp.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.poncegamez.ricknmortyapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var listBinding: FragmentListBinding
    private lateinit var listAdapter: ListAdapter
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listBinding = FragmentListBinding.inflate(inflater, container, false)
        setUpAdapter()
        setUpRecyclerView()
        addSubscriptions()
        return listBinding.root
    }

    private fun setUpAdapter() {
        listAdapter = ListAdapter()
        listAdapter.setOnItemClickListener { character ->
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailFragment(characterId = character.id)
            )
        }
    }

    private fun setUpRecyclerView() {
        listBinding.listRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun addSubscriptions() {
        listBinding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.listData.collect {
                    listAdapter.submitData(it)
                }
            }
        }
    }
}