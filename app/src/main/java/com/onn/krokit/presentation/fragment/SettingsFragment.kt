package com.onn.krokit.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.onn.krokit.R
import com.onn.krokit.databinding.FragmentSettingsBinding
import com.onn.krokit.presentation.GameViewModel

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    private val viewModel by lazy { ViewModelProvider(requireActivity())[GameViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_menuFragment)
        }
        getLiveDataTheme()
        setColorTheme()
        observeLiveData()
    }

    private fun getLiveDataTheme(){
        if(viewModel.setLightTheme.value == true){
            binding.cardLight.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.light_theme
                )
            )
            binding.cardDark.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.transparent
                )
            )
        } else {
            binding.cardLight.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.transparent
                )
            )
            binding.cardDark.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.dark_theme
                )
            )
        }
    }

    private fun setColorTheme(){
        binding.cardLight.setOnClickListener {
            viewModel.setLightTheme.value = true
        }
        binding.cardDark.setOnClickListener {
            viewModel.setLightTheme.value = false
        }
    }

    private fun observeLiveData(){
        viewModel.setLightTheme.observe(viewLifecycleOwner){
            if (it){
                binding.cardLight.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.light_theme
                    )
                )
                binding.cardDark.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.transparent
                    )
                )
            } else {
                binding.cardLight.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.transparent
                    )
                )
                binding.cardDark.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.dark_theme
                    )
                )
            }
        }
    }
}