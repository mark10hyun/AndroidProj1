package com.example.proj1.viewmodel
import android.app.Application
import com.example.proj1.CountRepository
import androidx.lifecycle.AndroidViewModel

class CountViewModel(application: Application): AndroidViewModel(application) {
    private val repository =
        CountRepository(application.applicationContext)
    fun getUserCount(name:String) = repository.getUserCount(name)
    fun setUserCount(name:String, count: Long) = repository.setUserCount(name, count )
}