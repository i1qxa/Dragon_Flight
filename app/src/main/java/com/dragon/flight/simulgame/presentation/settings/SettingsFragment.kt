package com.dragon.flight.simulgame.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
    }

    private fun setupHeader(){
        OutlinedText(
            binding.tvOptionsHeader,
            binding.tvOptionsHeader2,
            binding.tvOptionsHeader3,
            binding.tvOptionsHeader4,
            8F,
            android.graphics.Color.RED
        ).makeTVOutlined()
    }

}