package com.example.zeyada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.zeyada.databinding.FragmentZeyadaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ZeyadaFragment : Fragment() {


    private lateinit var binding: FragmentZeyadaBinding

    private lateinit var viewModel: ZeyadaNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentZeyadaBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initFunction()
        initViewModel()
        clickedView()

    }

    private fun initFunction() {
        render()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[ZeyadaNumberViewModel::class.java]
    }

    private fun clickedView() {
        binding.btnAddNumber.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(ZeyadaIntent.AddNumber)
            }
        }
    }

    private fun render() {
        lifecycleScope.launch (Dispatchers.Main) {
            viewModel.state.collect {
                when (it) {
                    is ZeyadaViewState.Idle -> binding.txtNumber.text = "Idle"
                    is ZeyadaViewState.Number -> binding.txtNumber.text = it.num.toString()
                    is ZeyadaViewState.Error -> binding.txtNumber.text = it.error
                }
            }
        }
    }

}