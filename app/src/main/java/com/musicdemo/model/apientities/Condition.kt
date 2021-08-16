package com.musicdemo.model.apientities


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Condition(
    @Json(name = "code")
    val code: Int,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "text")
    val text: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(code)
        parcel.writeString(icon)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Condition> {
        override fun createFromParcel(parcel: Parcel): Condition {
            return Condition(parcel)
        }

        override fun newArray(size: Int): Array<Condition?> {
            return arrayOfNulls(size)
        }
    }
}