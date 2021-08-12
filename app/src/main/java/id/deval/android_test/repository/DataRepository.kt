package id.deval.android_test.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.api.ApiInterface
import id.deval.android_test.model.Issue
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.Repository
import id.deval.android_test.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(val apiInterface: ApiInterface) {

    private lateinit var mUsers: MediatorLiveData<ModelWrapper<User>>
    private lateinit var mRepo: MediatorLiveData<ModelWrapper<Repository>>
    private lateinit var mIssue: MediatorLiveData<ModelWrapper<Issue>>

    fun getUsers(page: Int = 1, q: String = "a"): LiveData<ModelWrapper<User>> {
        var result = apiInterface.getUsers(query = q, page = page)
        mUsers = MediatorLiveData()
        result.enqueue(object : Callback<ModelWrapper<User>> {

            override fun onResponse(
                call: Call<ModelWrapper<User>>,
                response: Response<ModelWrapper<User>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    mUsers.postValue(data)
                    Log.d(TAG, "onResponse: isSuccessful ${mUsers.value}")
                } else if (!response.isSuccessful) {
                    Log.d(TAG, "onResponse: not successfull $response")
                }
            }

            override fun onFailure(call: Call<ModelWrapper<User>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
        return mUsers
    }

    fun getRepo(page: Int = 1, q: String = "a"): LiveData<ModelWrapper<Repository>> {
        val result = apiInterface.getRepositories(query = q, page = page)
        mRepo = MediatorLiveData()
        result.enqueue(object : Callback<ModelWrapper<Repository>> {
            override fun onResponse(
                call: Call<ModelWrapper<Repository>>,
                response: Response<ModelWrapper<Repository>>
            ) {
                if (response.isSuccessful) {
                    mRepo.postValue(response.body())
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<ModelWrapper<Repository>>, t: Throwable) {
            }

        })
        return mRepo
    }

    fun getIssue(page: Int = 1, q: String = "a"): LiveData<ModelWrapper<Issue>> {
        val result = apiInterface.getIssues(query = q, page = page)
        mIssue = MediatorLiveData()
        result.enqueue(object : Callback<ModelWrapper<Issue>> {
            override fun onResponse(
                call: Call<ModelWrapper<Issue>>,
                response: Response<ModelWrapper<Issue>>
            ) {
                if (response.isSuccessful) {
                    mIssue.postValue(response.body())
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<ModelWrapper<Issue>>, t: Throwable) {
            }

        })
        return mIssue
    }
}