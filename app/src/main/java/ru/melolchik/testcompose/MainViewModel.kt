package ru.melolchik.testcompose.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _isFollowing = MutableLiveData<Boolean>(false)
    val isFollowing : LiveData<Boolean> = _isFollowing

    fun changeFollowingStatus(){
        val value = _isFollowing.value ?: false
        _isFollowing.value = !value
    }
}