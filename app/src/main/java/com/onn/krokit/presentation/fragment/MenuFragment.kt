package com.onn.krokit.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.onn.krokit.R
import com.onn.krokit.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    lateinit var binding: FragmentMenuBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btGame.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
        }
        binding.btHistory.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_historyFragment)
        }
        binding.btSettings.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
        }
        binding.btDescription.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_descriptionFragment)
        }

    }

}