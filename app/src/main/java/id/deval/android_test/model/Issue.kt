package id.deval.android_test.model

import com.google.gson.annotations.SerializedName

data class Issue(

    @field:SerializedName("title")
    var title: String? = "",

    @field:SerializedName("created_at")
    var createDate: String? = "",

    @field:SerializedName("updated_at")
    var updateDate: String? = "",

    @field:SerializedName("comments")
    var comments: Int? = -1
)
