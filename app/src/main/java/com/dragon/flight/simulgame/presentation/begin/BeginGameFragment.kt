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
import android.widget.TextView
import androidx.core.text.getSpans
import androidx.fragment.app.Fragment
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.data.launchNewFragment
import com.dragon.flight.simulgame.databinding.FragmentBeginGameBinding
import com.dragon.flight.simulgame.presentation.about.AboutFragment
import com.dragon.flight.simulgame.presentation.settings.SettingsFragment

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
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners(){
        binding.btnPravila.setOnClickListener {
            parentFragmentManager.launchNewFragment(AboutFragment())
        }
        binding.btnNastroiki.setOnClickListener{
            parentFragmentManager.launchNewFragment(SettingsFragment())
        }
    }

    private fun setupHeader() {
        val outlinedTV = OutlinedText(
            binding.tvAppName,
            binding.tvAppName2,
            binding.tvAppName3,
            binding.tvAppName4,
            8F,
            Color.RED
        )
        outlinedTV.makeTVOutlined()
    }

}