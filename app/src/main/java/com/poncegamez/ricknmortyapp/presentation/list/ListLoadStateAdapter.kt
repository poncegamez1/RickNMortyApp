package com.poncegamez.ricknmortyapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poncegamez.ricknmortyapp.databinding.HeaderFooterLoadStateBinding

class ListLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ListLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = HeaderFooterLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding : HeaderFooterLoadStateBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.loadStateButton.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            binding.apply {
                progressBarLoadState.isVisible = loadState is LoadState.Loading
                loadStateButton.isVisible = loadState !is LoadState.Loading
                loadStateTextView.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}