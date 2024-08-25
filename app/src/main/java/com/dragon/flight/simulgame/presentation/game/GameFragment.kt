package com.dragon.flight.simulgame.presentation.game

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.OutlinedText
import com.dragon.flight.simulgame.data.launchNewFragment
import com.dragon.flight.simulgame.databinding.FragmentGameBinding
import com.dragon.flight.simulgame.presentation.game_result.GameResultFragment
import com.dragon.flight.simulgame.presentation.settings.SettingsFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameFragment : Fragment() {

    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<GameViewModel>()
    private val listOfItems by lazy {
        listOf(
            binding.item1,
            binding.item2,
            binding.item3,
            binding.item4,
            binding.item5,
            binding.item6
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
        initTVOutlined()
        setupListOfItems()
        setupBtnClickListeners()
        observeData()
        observeGameLose()
        observeGameWin()
        observeTimer()
    }

    private fun observeGameLose(){
        viewModel.isGameLose.observe(viewLifecycleOwner){
            parentFragmentManager.launchNewFragment(GameResultFragment.newInstance(0))
        }
    }

    private fun observeGameWin(){
        viewModel.isGameWin.observe(viewLifecycleOwner){
            parentFragmentManager.launchNewFragment(GameResultFragment.newInstance(it))
        }
    }

    private fun observeTimer(){
        viewModel.timerLD.observe(viewLifecycleOwner){
            val time = convertTime(it)
            binding.tvTimer.text = time
            binding.tvTimer2.text = time
            binding.tvTimer3.text = time
            binding.tvTimer4.text = time
        }
    }

    private fun convertTime(timeInSeconds:Int):String{
        return String.format("%02d:%02d", timeInSeconds / 60, timeInSeconds % 60)
    }

    private fun observeData(){
        viewModel.scoreLD.observe(viewLifecycleOwner){
            binding.tvScoreValue.text = it.toString()
            binding.tvScoreValue2.text = it.toString()
            binding.tvScoreValue3.text = it.toString()
            binding.tvScoreValue4.text = it.toString()
        }
    }

    private fun setupBtnClickListeners(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnSettings.setOnClickListener {
            parentFragmentManager.launchNewFragment(SettingsFragment())
        }
        binding.btnLeft.setOnClickListener {
            viewModel.moveDragon(true)
        }
        binding.btnRight.setOnClickListener {
            viewModel.moveDragon(false)
        }
    }

    private fun setupListOfItems() {
        lifecycleScope.launch {
            delay(200)
            viewModel.setupListOfItems(listOfItems, binding.dragon)
            viewModel.setupLimits(
                Pair(binding.item1.x, binding.item1.y),
                Pair(
                    (binding.btnRight.x - binding.item1.width),
                    (binding.dragon.y + binding.dragon.height/3)
                )
            )
        }
    }

    private fun initTVOutlined() {
        OutlinedText(
            binding.tvScore,
            binding.tvScore2,
            binding.tvScore3,
            binding.tvScore4,
            4F,
            Color.BLUE
        ).makeTVOutlined()
        OutlinedText(
            binding.tvScoreValue,
            binding.tvScoreValue2,
            binding.tvScoreValue3,
            binding.tvScoreValue4,
            4F,
            Color.BLUE
        ).makeTVOutlined()
        OutlinedText(
            binding.tvTimer,
            binding.tvTimer2,
            binding.tvTimer3,
            binding.tvTimer4,
            4F,
            Color.BLUE
        ).makeTVOutlined()
    }

}