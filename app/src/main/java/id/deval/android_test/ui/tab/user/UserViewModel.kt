package id.deval.android_test.ui.tab.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.User
import id.deval.android_test.repository.DataRepository


class UserViewModel : ViewModel() {

    lateinit var dataRepository: DataRepository
    var page = 1
    var q = "a"

    val users: LiveData<ModelWrapper<User>>
        get() {
//            loadUsers()
            Log.d(TAG, "getUser ViewModel:  ${dataRepository.getUsers().value}")
            return dataRepository.getUsers()
        }

    fun UserViewModel(
        dataRepository: DataRepository
    ) {
        this.dataRepository = dataRepository
    }

    fun loadUsers(page: Int = 1, q: String = "a") {
        Log.d(TAG, "loadUsers: running")
        dataRepository.loadUsers(page, q)
    }
}