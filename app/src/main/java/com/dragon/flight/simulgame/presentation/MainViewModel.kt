package com.dragon.flight.simulgame.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragon.flight.simulgame.data.BASE_DURATION
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    private var appendText = "."
    private var delay = 0
    val loadingTextLD = MutableLiveData<String>()
    val shouldFinishLoading = MutableLiveData<Boolean>()
    init {
        viewModelScope.launch {
            while (delay<= BASE_DURATION){
                delay(300)
                updateAppendText()
                loadingTextLD.postValue(appendText)
                delay++
            }
        shouldFinishLoading.postValue(true)
        }
    }

    private fun updateAppendText(){
        appendText = when(appendText){
            "." -> ".."
            ".." -> "..."
            else -> "."
        }
    }

}