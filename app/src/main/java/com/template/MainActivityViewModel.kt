package com.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val _parts = MutableLiveData<Array<String>>().apply {
        value = arrayOf<String>("p1", "p2", "p3", "p4", "p5")
    }
    val parts: LiveData<Array<String>> =  _parts


    private val _postsIndex = MutableLiveData<Int>().apply {
        value = 0
    }
    val postsIndex: LiveData<Int> = _postsIndex

    fun postPostsIndex(index: Int){
        _postsIndex.postValue(index)
    }

    fun postParts(parts: Array<String>?){
        _parts.postValue(parts)
    }



}