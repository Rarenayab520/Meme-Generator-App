package com.nayab.memegeneratorapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayab.memegeneratorapp.data.RedditMeme
import com.nayab.memegeneratorapp.repository.MemeRepository
import kotlinx.coroutines.launch

class MemeViewModel : ViewModel() {

    private val repository = MemeRepository()

    private val _memeData = MutableLiveData<RedditMeme?>()
    val memeData: LiveData<RedditMeme?> = _memeData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isLiked = false

    fun loadRandomMeme() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val meme = repository.getRandomMeme()
                _memeData.value = meme
            } catch (e: Exception) {
                _memeData.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLike(): Boolean {
        isLiked = !isLiked
        return isLiked
    }
}
