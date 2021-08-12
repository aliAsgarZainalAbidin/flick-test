package id.deval.android_test.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.deval.android_test.BuildConfig.TAG
import id.deval.android_test.api.ApiInterface
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(val apiInterface: ApiInterface) {

    private lateinit var mUsers: MediatorLiveData<ModelWrapper<User>>

    fun getUsers(page: Int = 1, q: String ="a"): LiveData<ModelWrapper<User>> {
        var result = apiInterface.getUsers(query = q, page = page)
        mUsers = MediatorLiveData()
        Log.d(TAG, "loadUsers: datarepository running")
        result.enqueue(object : Callback<ModelWrapper<User>> {

            override fun onResponse(
                call: Call<ModelWrapper<User>>,
                response: Response<ModelWrapper<User>>
            ) {
                Log.d(TAG, "onResponse: enqueue running")
                if (response.isSuccessful) {
                    val data = response.body()
                    val liveData: MutableLiveData<ModelWrapper<User>> = MutableLiveData(data)
//                    mUsers.addSource(liveData, object : Observer<ModelWrapper<User>> {
//                        override fun onChanged(it: ModelWrapper<User>?) {
//                            if (it != null) {
//                                mUsers.postValue(it)
//                            }
//                            Log.d(TAG, "onChanged: addSource Running")
//                        }
//                    })
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
}