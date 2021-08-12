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
            Log.d(TAG, "getUser ViewModel:  ${dataRepository.getUsers().value}")
            return dataRepository.getUsers(page, q)
        }

}