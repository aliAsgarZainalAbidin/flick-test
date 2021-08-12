package id.deval.android_test.api

import androidx.lifecycle.MutableLiveData
import id.deval.android_test.model.Issue
import id.deval.android_test.model.ModelWrapper
import id.deval.android_test.model.Repository
import id.deval.android_test.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("users?")
    fun getUsers(
        @Query("q") query: String = "a",
        @Query("per_page") per_page: Int = 10,
        @Query("page") page: Int? = 1
    ): Call<ModelWrapper<User>>

    @GET("users?")
    fun getMUsers(
        @Query("q") query: String = "a",
        @Query("per_page") per_page: Int = 10,
        @Query("page") page: Int? = 1
    ): Call<MutableLiveData<ModelWrapper<User>>>

    @GET("repositories?")
    fun getRepositories(
        @Query("q") query: String = "a",
        @Query("per_page") per_page: Int = 10,
        @Query("page") page: Int? = 1
    ): Call<ModelWrapper<Repository>>

    @GET("issues?")
    fun getIssues(
        @Query("q") query: String = "a",
        @Query("per_page") per_page: Int = 10,
        @Query("page") page: Int? = 1
    ): Call<ModelWrapper<Issue>>
}