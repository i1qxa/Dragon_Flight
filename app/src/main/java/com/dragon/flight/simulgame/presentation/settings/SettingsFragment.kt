package com.dragon.flight.simulgame.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.data.dataStore
import com.dragon.flight.simulgame.data.isMusicOnKey
import com.dragon.flight.simulgame.data.isSoundOnKey
import com.dragon.flight.simulgame.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }
    private var isMusicOn = true
    private var isSoundOn = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        observeSettings()
        setupBtnClickListeners()
    }

    private fun observeSettings() {
        requireContext().dataStore.data.asLiveData().observe(viewLifecycleOwner) {
            isMusicOn = it[isMusicOnKey] ?: true
            val res = if (isMusicOn) R.drawable.btn_music_on else R.drawable.btn_music_off
            binding.btnMusic.setImageResource(res)
        }
        requireContext().dataStore.data.asLiveData().observe(viewLifecycleOwner) {
            isSoundOn = it[isSoundOnKey] ?: true
            val res = if (isSoundOn) R.drawable.btn_sound_on else R.drawable.btn_sound_off
            binding.btnSound.setImageResource(res)
        }
    }

    private fun setupBtnClickListeners() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnMusic.setOnClickListener {
            lifecycleScope.launch {
                requireContext().dataStore.edit {
                    it[isMusicOnKey] = !isMusicOn
                }
            }
        }
        binding.btnSound.setOnClickListener {
            lifecycleScope.launch {
                requireContext().dataStore.edit {
                    it[isSoundOnKey] = !isSoundOn
                }
            }
        }
    }

    private fun setupHeader() {
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