package com.dragon.flight.simulgame.presentation.game_activity

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.dataStore
import com.dragon.flight.simulgame.data.isMusicOnKey
import com.dragon.flight.simulgame.data.launchNewFragment
import com.dragon.flight.simulgame.presentation.begin.BeginGameFragment
import com.dragon.flight.simulgame.presentation.game_result.GameResultFragment
import com.dragon.flight.simulgame.presentation.lvls.LvlsFragment
import com.dragon.flight.simulgame.presentation.quit.QuitFragment
import com.dragon.flight.simulgame.presentation.settings.SettingsFragment

class DragonGameActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dragon_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        observeSound()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.conteinerDragon)
        when(fragment){
            is BeginGameFragment ->{
                supportFragmentManager.launchNewFragment(QuitFragment())
            }
            is SettingsFragment ->{
                supportFragmentManager.launchNewFragment(BeginGameFragment())
            }
            is GameResultFragment ->{
                supportFragmentManager.launchNewFragment(LvlsFragment())
            }
            else ->{
                super.onBackPressed()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if(mediaPlayer!=null){
            mediaPlayer!!.release()
            mediaPlayer=null
        }
    }

    private fun playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        } else mediaPlayer!!.start()
    }

    private fun stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    private fun observeSound(){
        dataStore.data.asLiveData().observe(this){
            if (it[isMusicOnKey] != false){
                playSound()
            }else{
                stopSound()
            }
        }
    }

}