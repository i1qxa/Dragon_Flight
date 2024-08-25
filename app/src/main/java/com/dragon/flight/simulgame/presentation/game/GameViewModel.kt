package com.dragon.flight.simulgame.presentation.game

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragon.flight.simulgame.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private var topLeftCorner = Pair(0F, 0F)
    private var bottomRightCorner = Pair(0F, 0F)
    private var listOfItems = mutableListOf<ImageView>()
    private var score = 0
    val scoreLD = MutableLiveData<Int>()

    @SuppressLint("StaticFieldLeak")
    private lateinit var dragon: ImageView

    fun setupListOfItems(list: List<ImageView>, dragonView: ImageView) {
        list.map {
            listOfItems.add(it)
        }
        dragon = dragonView
        launchGame()
        observeGame()
    }

    fun setupLimits(topLeft: Pair<Float, Float>, bottomRight: Pair<Float, Float>) {
        topLeftCorner = topLeft
        bottomRightCorner = bottomRight
    }

    private fun launchGame() {
        viewModelScope.launch {
            listOfItems.map {
                delay(1000)
                launchFallingDawnAnim(it)
            }
        }
    }

    private fun launchFallingDawnAnim(item: ImageView) {
        item.visibility = View.VISIBLE
        if (Random.nextBoolean()) {
            item.setImageResource(R.drawable.ruby)
        } else {
            item.setImageResource(R.drawable.bomb)
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
                    val itemX = Pair(it.x, (it.x+it.width))
                    if (dragonX.first in itemX.first..itemX.second || itemX.first in dragonX.first..dragonX.second){
                        val score = (scoreLD.value ?: 0) + 10
                        scoreLD.postValue(score)
                        launchFallingDawnAnim(it)
                    }
                }
            }
        }
    }

    fun moveDragon(isMoveLeft: Boolean) {
        var newX = dragon.x
        if (isMoveLeft) {
            newX -= 60F
            if (newX < topLeftCorner.first) newX = topLeftCorner.first

        } else {
            newX += 60F
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

}