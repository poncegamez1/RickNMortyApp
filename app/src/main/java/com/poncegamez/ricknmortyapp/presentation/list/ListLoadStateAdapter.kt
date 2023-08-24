package com.poncegamez.ricknmortyapp.presentation.list

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
        holder.bind(loadState, holder.itemView.context)
    }

    inner class LoadStateViewHolder(private val binding : HeaderFooterLoadStateBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.loadStateButton.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState, context: Context){
            binding.apply {
                val isInternetConnected = checkInternetConnectivity(context)
                progressBarLoadState.isVisible = loadState is LoadState.Loading
                loadStateButton.isVisible = !isInternetConnected
                loadStateTextView.isVisible = !isInternetConnected
            }
        }
    }

}

fun checkInternetConnectivity(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}