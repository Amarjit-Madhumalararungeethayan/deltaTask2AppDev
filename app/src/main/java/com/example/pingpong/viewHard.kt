package com.example.pingpong

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class viewHard(application : Application):AndroidViewModel(application) {

    private val repository = DataStoreS(application)

    val readFrom = repository.read.asLiveData()

    fun saveTo(miNum : Int) = viewModelScope.launch(Dispatchers.IO){
        repository.saveTo(miNum)
    }
}