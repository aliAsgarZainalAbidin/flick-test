package id.deval.android_test.model

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("login")
    var login: String? = "",

    @field:SerializedName("id")
    var id: Int? = -1,

    @field:SerializedName("avatar_url")
    var avatar_url: String? = ""
)
