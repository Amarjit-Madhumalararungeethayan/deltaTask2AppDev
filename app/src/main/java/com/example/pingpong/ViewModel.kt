package com.example.pingpong

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel(application : Application):AndroidViewModel(application) {

    private val repository = DataStoreR(application)

    val readFrom = repository.read.asLiveData()

    fun saveTo1(myNum1 : Int) = viewModelScope.launch(Dispatchers.IO){
        repository.saveTo(myNum1)
    }
    }
