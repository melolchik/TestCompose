package ru.melolchik.testcompose.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.melolchik.testcompose.InstagramModel
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<InstagramModel>().apply {
        repeat(500){
            add( InstagramModel(it, "Title : $it", isFollowed = Random.nextBoolean()))
        }
    }

    private val _models = MutableLiveData<List<InstagramModel>>(initList)
    val models : LiveData<List<InstagramModel>> = _models

    fun changeFollowingStatus(model: InstagramModel){
        val modifiedList = _models.value.toMutableList() ?: mutableListOf()

        modifiedList.replaceAll{
            if(it == model){
                it.copy(isFollowed = !it.isFollowed)
            }else{
                it
            }
        }
        _models.value = modifiedList
    }
}