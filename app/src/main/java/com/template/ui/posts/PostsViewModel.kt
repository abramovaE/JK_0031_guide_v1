package com.template.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text


    private val _content = MutableLiveData<List<String>>().apply {
        value = arrayListOf<String>("f1", "f2", "f3", "f4", "f5")
    }
    val content: LiveData<List<String>> =  _content

}