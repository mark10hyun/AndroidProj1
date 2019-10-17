package CountViewModel

import android.app.Application

class CountViewModel(application: Application) {
    fun getUserCount(name:String)= repository.getUserCount(name)
    fun setUserCount(name: String, count: Long)= repository.setUserCount
}