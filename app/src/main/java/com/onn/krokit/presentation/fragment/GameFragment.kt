package com.onn.krokit.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onn.krokit.R
import com.onn.krokit.databinding.FragmentGameBinding
import com.onn.krokit.presentation.GameAdapter
import com.onn.krokit.presentation.GameViewModel
import com.onn.krokit.presentation.HistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GameFragment : Fragment() {
    lateinit var binding: FragmentGameBinding
    lateinit var adapter: GameAdapter
    private val viewModel by lazy { ViewModelProvider(requireActivity())[GameViewModel::class.java] }
    private var vinImage = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GameAdapter(requireActivity())
        binding.recyclerId.adapter = adapter
        adapter.listImage.clear()
        adapter.listImage.addAll(viewModel.imageList1)
        setColor()
        lifecycleScope.launch(Dispatchers.IO) {
            if (viewModel.setLightTheme.value == true){
                while (true) {
                    delay(700)
                    binding.imArrowLeft.setImageResource(R.drawable.ic_arrow_left)
                    binding.imArrowRight.setImageResource(R.drawable.ic_arrow_right)
                    delay(700)
                    binding.imArrowLeft.setImageResource(R.drawable.ic_arrow_left_black)
                    binding.imArrowRight.setImageResource(R.drawable.ic_arrow_right_black)
                }
            } else {
                while (true) {
                    delay(700)
                    binding.imArrowLeft.setImageResource(R.drawable.ic_dark_left)
                    binding.imArrowRight.setImageResource(R.drawable.ic_dark_right)
                    delay(700)
                    binding.imArrowLeft.setImageResource(R.drawable.ic_arrow_left_black)
                    binding.imArrowRight.setImageResource(R.drawable.ic_arrow_right_black)
                }
            }

        }

        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                if (viewModel.imagePosition > 0) {
                    delay(1000)
                    withContext(Dispatchers.Main) {
                        binding.button2.visibility = View.VISIBLE
                        setTextButton()
                    }
                }
            }
        }

        binding.button2.setOnClickListener {
            viewModel.listResult.add(viewModel.imageList1[viewModel.imagePosition])
            if (viewModel.countImage == 3) viewModel.countImage = 0
            viewModel.countImage++
            if (binding.button2.text == "Vin" && viewModel.countImage == 3){
                binding.imageView9.setImageResource(viewModel.imageList1[viewModel.imagePosition])
            }
            if (binding.button2.text == "Vin" && viewModel.countImage == 2){
                binding.imageView10.setImageResource(viewModel.imageList1[viewModel.imagePosition])
            }
            if (binding.button2.text == "Vin" && viewModel.countImage == 1){
                binding.imageView8.setImageResource(viewModel.imageList1[viewModel.imagePosition])
            }
            lifecycleScope.launch {
                adapter.listImage.clear()
                adapter.notifyDataSetChanged()
                delay(200)
                adapter.listImage.addAll(viewModel.imageList1)
                adapter.notifyDataSetChanged()
            }
            binding.button2.visibility = View.GONE
            viewModel.imagePosition = 0
        }

        binding.btBack.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
        }
    }

    private fun setTextButton(){
        if (viewModel.imagePosition == 1 || viewModel.imagePosition == 2 || viewModel.imagePosition == 3 ||
             viewModel.imagePosition == 7 || viewModel.imagePosition == 8){
            binding.button2.text = "Vin"
        } else {binding.button2.text = "Lose"}
    }

    private fun setColor(){
        if (viewModel.setLightTheme.value == true){
            binding.imArrowLeft.setImageResource(R.drawable.ic_arrow_left)
            binding.imArrowRight.setImageResource(R.drawable.ic_arrow_right)
            binding.button2.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.light_theme))
            binding.btBack.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.light_theme))
            binding.textView9.setTextColor(ContextCompat.getColor(requireActivity(), R.color.light_text))
            binding.textView10.setTextColor(ContextCompat.getColor(requireActivity(), R.color.light_text))
        } else {
            binding.imArrowLeft.setImageResource(R.drawable.ic_dark_left)
            binding.imArrowRight.setImageResource(R.drawable.ic_dark_right)
            binding.button2.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.dark_theme))
            binding.btBack.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.dark_theme))
            binding.textView9.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_text))
            binding.textView10.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_text))
        }
    }
}