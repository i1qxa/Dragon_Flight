package com.dragon.flight.simulgame.presentation.game_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.launchNewFragment
import com.dragon.flight.simulgame.presentation.begin.BeginGameFragment
import com.dragon.flight.simulgame.presentation.game_result.GameResultFragment
import com.dragon.flight.simulgame.presentation.lvls.LvlsFragment
import com.dragon.flight.simulgame.presentation.quit.QuitFragment
import com.dragon.flight.simulgame.presentation.settings.SettingsFragment

class DragonGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dragon_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
//        if (supportFragmentManager.findFragmentById(R.id.conteinerDragon) is BeginGameFragment) {
//            supportFragmentManager.launchNewFragment(QuitFragment())
//        } else {
//            super.onBackPressed()
//        }
    }

}