package com.dragon.flight.simulgame.presentation.game_result

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.data.dataStore
import com.dragon.flight.simulgame.data.isSoundOnKey
import com.dragon.flight.simulgame.data.launchNewFragment
import com.dragon.flight.simulgame.databinding.FragmentGameResultBinding
import com.dragon.flight.simulgame.presentation.game.GameFragment
import com.dragon.flight.simulgame.presentation.lvls.LvlsFragment

private const val SCORE = "score"

class GameResultFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private var score: Int = 0
    private val binding by lazy { FragmentGameResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(SCORE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun observeSoundSettings(isWin: Boolean) {
        requireContext().dataStore.data.asLiveData().observe(viewLifecycleOwner) {
            if(it[isSoundOnKey] != false){
                val soundId = if (isWin) R.raw.win else R.raw.lose
                playSound(soundId)
            }
        }
    }

    private fun playSound(soundId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(requireContext(), soundId)
            mediaPlayer!!.isLooping = false
            mediaPlayer!!.start()
        } else mediaPlayer!!.start()
    }

    private fun initFragment() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.launchNewFragment(LvlsFragment())
        }
        if (score > 0) {
            launchWin()
            observeSoundSettings(true)
        } else {
            launchLose()
            observeSoundSettings(false)
        }
    }

    private fun launchLose() {
        binding.winBckgr.visibility = View.GONE
        binding.resHeader.setImageResource(R.drawable.you_lost)
        binding.resDescription.setImageResource(R.drawable.you_caught_a_bomb)
        binding.tvScore.visibility = View.GONE
        binding.tvScore2.visibility = View.GONE
        binding.tvScore3.visibility = View.GONE
        binding.tvScore4.visibility = View.GONE
        binding.mainBtn.setImageResource(R.drawable.try_again)
        binding.mainBtn.setOnClickListener {
            parentFragmentManager.launchNewFragment(GameFragment())
        }
    }

    private fun launchWin() {
        binding.winBckgr.visibility = View.VISIBLE
        binding.resHeader.setImageResource(R.drawable.you_win)
        binding.resDescription.setImageResource(R.drawable.your_score)
        binding.tvScore.text = score.toString()
        binding.tvScore2.text = score.toString()
        binding.tvScore3.text = score.toString()
        binding.tvScore4.text = score.toString()
        binding.tvScore.visibility = View.VISIBLE
        binding.tvScore2.visibility = View.VISIBLE
        binding.tvScore3.visibility = View.VISIBLE
        binding.tvScore4.visibility = View.VISIBLE
        OutlinedText(
            binding.tvScore,
            binding.tvScore2,
            binding.tvScore3,
            binding.tvScore4,
            8F,
            Color.BLUE
        ).makeTVOutlined()
        binding.mainBtn.setImageResource(R.drawable.top_levels)
        binding.mainBtn.setOnClickListener {
            parentFragmentManager.launchNewFragment(LvlsFragment())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(score: Int) =
            GameResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(SCORE, score)
                }
            }
    }
}