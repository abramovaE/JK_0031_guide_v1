package com.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val _postsIndex = MutableLiveData<Int>().apply {
        value = 0
    }
    val postsIndex: LiveData<Int> = _postsIndex

    fun postPostsIndex(index: Int){
        _postsIndex.postValue(index)
    }
}