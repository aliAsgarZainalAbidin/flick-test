package id.deval.android_test.ui.tab.issue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.deval.android_test.model.Issue
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.Repository
import id.deval.android_test.repository.DataRepository

class IssueViewModel : ViewModel() {
    lateinit var dataRepository: DataRepository
    var page = 1
    var q = "a"

    val issue : LiveData<ModelWrapper<Issue>>
        get() {
            return dataRepository.getIssue(page, q)
        }
}