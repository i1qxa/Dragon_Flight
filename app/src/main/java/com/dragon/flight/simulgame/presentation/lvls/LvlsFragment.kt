package com.dragon.flight.simulgame.presentation.lvls

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.asLiveData
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.data.dataStore
import com.dragon.flight.simulgame.data.lastCompleteLvlKey
import com.dragon.flight.simulgame.data.launchNewFragment
import com.dragon.flight.simulgame.databinding.FragmentLvlsBinding
import com.dragon.flight.simulgame.presentation.game.GameFragment


class LvlsFragment : Fragment() {

    private val binding by lazy { FragmentLvlsBinding.inflate(layoutInflater) }
    private val listOfLvlButtons by lazy {
        listOf(
            binding.lvl1,
            binding.lvl2,
            binding.lvl3,
            binding.lvl4,
            binding.lvl5,
            binding.lvl6,
            binding.lvl7,
            binding.lvl8,
            binding.lvl9,
            binding.lvl10,
            binding.lvl11,
            binding.lvl12,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        observeLastCompleteLvl()
        setupBtnBackClickListener()
    }

    private fun observeLastCompleteLvl() {
        requireContext().dataStore.data.asLiveData().observe(viewLifecycleOwner) {
            val lastCompleteLvl = (it[lastCompleteLvlKey] ?: 1) - 1
            var counter = 0
            while (counter < listOfLvlButtons.size) {
                if (counter <= lastCompleteLvl) {
                    val btn = listOfLvlButtons[counter]
                    makeButtonActive(btn)
                    setupLvlClickListener(btn)
                }
                counter++
            }
        }
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupLvlClickListener(btnLvl: TextView) {
        btnLvl.setOnClickListener {
            parentFragmentManager.launchNewFragment(GameFragment())
        }
    }

    private fun makeButtonActive(btnLvl: TextView) {
        btnLvl.setBackgroundResource(R.drawable.btn_lvl_complete)
    }

    private fun setupHeader() {
        OutlinedText(
            binding.tvLvlsHeader,
            binding.tvLvlsHeader2,
            binding.tvLvlsHeader3,
            binding.tvLvlsHeader4,
            8F,
            Color.RED
        ).makeTVOutlined()
    }

}