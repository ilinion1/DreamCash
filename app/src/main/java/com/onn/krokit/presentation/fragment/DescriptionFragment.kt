package com.onn.krokit.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.onn.krokit.R
import com.onn.krokit.databinding.FragmentDescriptionBinding


class DescriptionFragment : Fragment() {
    lateinit var binding: FragmentDescriptionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBack.setOnClickListener {
            findNavController().navigate(R.id.action_descriptionFragment_to_menuFragment)
        }
    }

}