package com.onn.krokit.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.onn.krokit.R
import com.onn.krokit.databinding.FragmentHistoryBinding
import com.onn.krokit.presentation.GameViewModel
import com.onn.krokit.presentation.HistoryAdapter


class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    lateinit var adapter: HistoryAdapter
    private val viewModel by lazy { ViewModelProvider(requireActivity())[GameViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryAdapter()
        binding.recyclerId.adapter = adapter
        adapter.listImage.clear()
        adapter.listImage.addAll(viewModel.listResult)
        adapter.notifyDataSetChanged()

        binding.btBack.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_menuFragment)
        }
    }

}