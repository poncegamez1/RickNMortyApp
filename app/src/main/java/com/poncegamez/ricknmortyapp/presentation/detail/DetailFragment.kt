package com.poncegamez.ricknmortyapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.poncegamez.ricknmortyapp.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(inflater, container,false)
        addSubscriptions()
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(this, Observer {
            detailBinding.detailProgressBar.isVisible = it
        })

        viewModel.getDetailFromServer(args.characterId)
    }

    private fun addSubscriptions(){
        viewModel.onDetailState.observe(viewLifecycleOwner) {detailCharacter ->
            if (detailCharacter != null) {
                detailBinding.nameTitleTextView.text = detailCharacter.name
                detailBinding.idDetailTextView.text = detailCharacter.id.toString()
                detailBinding.statusDetailTextView.text = detailCharacter.status
                detailBinding.specieDetailTextView.text = detailCharacter.species
                detailBinding.typeDetailTextView.text = detailCharacter.type
                detailBinding.genderDetailTextView.text = detailCharacter.gender
                Picasso.get().load(detailCharacter.image).into(detailBinding.detailImageView)
            }

        }
    }



}