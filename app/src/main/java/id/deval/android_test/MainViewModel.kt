package id.deval.android_test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.deval.android_test.model.Issue
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.Repository
import id.deval.android_test.model.User
import id.deval.android_test.repository.DataRepository

class MainViewModel : ViewModel() {
    lateinit var dataRepository: DataRepository
    var page = 1
    var q = "a"

    val issue : LiveData<ModelWrapper<Issue>>
        get() {
            return dataRepository.getIssue(page, q)
        }

    val repo : LiveData<ModelWrapper<Repository>>
        get() {
            return dataRepository.getRepo(page, q)
        }

    val users: LiveData<ModelWrapper<User>>
        get() {
            return dataRepository.getUsers(page, q)
        }
}