package com.dragon.flight.simulgame.presentation.begin

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.getSpans
import androidx.fragment.app.Fragment
import com.dragon.flight.simulgame.databinding.FragmentBeginGameBinding

class BeginGameFragment : Fragment() {

    private val binding by lazy { FragmentBeginGameBinding.inflate(layoutInflater) }

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

    private fun setupHeader() {
    }

}