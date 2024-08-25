package com.dragon.flight.simulgame.presentation.quit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.databinding.FragmentQuitBinding


class QuitFragment : Fragment() {

    private val binding by lazy { FragmentQuitBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        setupBtnClickListeners()
    }

    private fun setupBtnClickListeners(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnNo.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnYes.setOnClickListener {
            requireActivity().finishAffinity()
        }
    }

    private fun setupHeader() {
        OutlinedText(
            binding.tvExitHeader,
            binding.tvExitHeader2,
            binding.tvExitHeader3,
            binding.tvExitHeader4,
            8F,
            android.graphics.Color.RED
        ).makeTVOutlined()
    }

}