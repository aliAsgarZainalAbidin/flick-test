package id.deval.android_test.api

import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("user?q={}")
    fun getUsers(): Call<ModelWrapper<User>>
}