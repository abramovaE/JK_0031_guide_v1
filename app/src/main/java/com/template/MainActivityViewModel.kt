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


    private val _partName = MutableLiveData<String>().apply {
        value = ""
    }
    val partName: LiveData<String> = _partName
    fun postPartName(name: String){
        _partName.postValue(name)
    }

}