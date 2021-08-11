package id.deval.android_test.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
open class ModelWrapper<T> : Parcelable {
    @field:SerializedName("total_count")
    open var totalCount: Int? = -1

    @field:SerializedName("incomplete_results")
    open var incompleteResults: Boolean? = false

    @field:SerializedName("items")
    open var items: T? = null
}