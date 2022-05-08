package com.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    private val _title = MutableLiveData<String>().apply {
        value = ""
    }
    val title: LiveData<String> = _title
    fun postTitle(title: String){
        _title.postValue(title)
    }
}