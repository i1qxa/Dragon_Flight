package com.dragon.flight.simulgame.presentation

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.databinding.ActivityMainBinding
import com.dragon.flight.simulgame.presentation.game_activity.DragonGameActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeLoadingText()
        checkFinishLoading()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun observeLoadingText(){
        viewModel.loadingTextLD.observe(this){
            binding.tvLoadingDots.text = it
        }
    }

    private fun checkFinishLoading(){
        viewModel.shouldFinishLoading.observe(this){
            if (it){
                val intent = Intent(this, DragonGameActivity::class.java)
                startActivity(intent)
            }
        }
    }

}