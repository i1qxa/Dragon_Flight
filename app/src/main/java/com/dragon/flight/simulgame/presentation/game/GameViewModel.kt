package com.dragon.flight.simulgame.presentation.game

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import android.widget.ImageView
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dragon.flight.simulgame.R
import com.dragon.flight.simulgame.data.BASE_GAME_DURATION
import com.dragon.flight.simulgame.data.BASE_MULTI
import com.dragon.flight.simulgame.data.dataStore
import com.dragon.flight.simulgame.data.lastCompleteLvlKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(private val application: Application) : AndroidViewModel(application) {

    private var topLeftCorner = Pair(0F, 0F)
    private var bottomRightCorner = Pair(0F, 0F)
    private var listOfItems = mutableListOf<ImageView>()
    val scoreLD = MutableLiveData<Int>()

    @SuppressLint("UseCompatLoadingForDrawables")
    private val bombImg = application.baseContext.getDrawable(R.drawable.bomb)

    @SuppressLint("UseCompatLoadingForDrawables")
    private val rubyImg = application.baseContext.getDrawable(R.drawable.ruby)
    val isGameLose = MutableLiveData<Any>()
    var timerValue = 0
    val timerLD = MutableLiveData<Int>()
    val isGameWin = MutableLiveData<Int>()
    private var lastCompleteLvl = 1

    @SuppressLint("StaticFieldLeak")
    private lateinit var dragon: ImageView

    fun setupListOfItems(list: List<ImageView>, dragonView: ImageView) {
        list.map {
            listOfItems.add(it)
        }
        dragon = dragonView
        launchGame()
        observeGame()
        viewModelScope.launch {
            application.dataStore.data.collect {
                lastCompleteLvl = it[lastCompleteLvlKey] ?: 1
                val timer = BASE_GAME_DURATION + lastCompleteLvl * BASE_MULTI
                timerValue = timer
                timerLD.postValue(timer)
                launchCountDawnTimer()
            }
        }
    }

    private fun launchCountDawnTimer() {
        viewModelScope.launch {
            while (timerValue > 0) {
                delay(1000)
                timerValue--
                timerLD.postValue(timerValue)
            }
            application.dataStore.edit {
                it[lastCompleteLvlKey] = lastCompleteLvl+1
            }
            val score = scoreLD.value ?: 0
            isGameWin.postValue(score)
        }

    }

    fun setupLimits(topLeft: Pair<Float, Float>, bottomRight: Pair<Float, Float>) {
        topLeftCorner = topLeft
        bottomRightCorner = bottomRight
    }

    private fun launchGame() {
        viewModelScope.launch {
            delay(300)
            listOfItems.map {
                launchFallingDawnAnim(it)
                delay(1000)
            }
        }
    }

    private fun launchFallingDawnAnim(item: ImageView) {
        item.visibility = View.VISIBLE
        if (getRandomBomb()) {
            item.setImageDrawable(rubyImg)
        } else {
            item.setImageDrawable(bombImg)
        }
        item.x = getRandomX()
        item.y = topLeftCorner.second
        item.animate().apply {
            duration = 10000
            yBy(bottomRightCorner.second)
            withEndAction {
                launchFallingDawnAnim(item)
            }
        }
    }

    private fun observeGame() {
        viewModelScope.launch {
            while (true) {
                delay(200)
                val dragonX = Pair(dragon.x, (dragon.x + dragon.width))
                listOfItems.filter { it.y > dragon.y }.map {
                    val itemX = Pair(it.x, (it.x + it.width))
                    if (dragonX.first in itemX.first..itemX.second || itemX.first in dragonX.first..dragonX.second) {
                        val score = (scoreLD.value ?: 0) + 10
                        scoreLD.postValue(score)
                        if (it.drawable == bombImg) {
                            isGameLose.postValue(Unit)
                        }
                    }
                    launchFallingDawnAnim(it)
                }
            }
        }
    }

    fun moveDragon(isMoveLeft: Boolean) {
        var newX = dragon.x
        if (isMoveLeft) {
            newX -= 80F
            if (newX < topLeftCorner.first) newX = topLeftCorner.first

        } else {
            newX += 80F
            if (newX > bottomRightCorner.first) newX = bottomRightCorner.first
        }
        dragon.animate().apply {
            duration = 200
            x(newX)
        }
    }

    private fun getRandomX(): Float {
        return topLeftCorner.first + (bottomRightCorner.first - topLeftCorner.first) * Random.nextFloat()
    }

    private fun getRandomBomb(): Boolean {
        val randomInt = 1 + Random.nextInt(5)
        return if (randomInt > 4) false else true
    }

}