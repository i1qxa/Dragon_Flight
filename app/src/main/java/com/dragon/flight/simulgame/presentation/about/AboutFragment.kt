package com.dragon.flight.simulgame.presentation.about

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private val binding by lazy { FragmentAboutBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextViews()
        setupBtnBackClickListener()
    }

    private fun initTextViews(){
        OutlinedText(
            binding.tvAboutHeader,
            binding.tvAboutHeader2,
            binding.tvAboutHeader3,
            binding.tvAboutHeader4,
            8F,
            Color.RED
        ).makeTVOutlined()
        binding.tvAbout.setShadowLayer(4F,4F,4F,Color.BLACK)
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}