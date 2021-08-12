package id.deval.android_test.ui.tab.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.Repository
import id.deval.android_test.repository.DataRepository

class RepositoryViewModel : ViewModel() {
    lateinit var dataRepository: DataRepository
    var page = 1
    var q = "a"

    val repo : LiveData<ModelWrapper<Repository>>
        get() {
            return dataRepository.getRepo(page, q)
        }
}