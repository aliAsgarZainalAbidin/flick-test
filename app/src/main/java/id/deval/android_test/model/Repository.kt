package id.deval.android_test.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @field:SerializedName("full_name")
    var fullName: String? = "",

    @field:SerializedName("description")
    var description: String? = "",

    @field:SerializedName("stargazers_count")
    var star: Int? = -1,

    @field:SerializedName("language")
    var language: String? = "",

    @field:SerializedName("forks_count")
    var fork: Int? = -1,

    var user: User?
)
