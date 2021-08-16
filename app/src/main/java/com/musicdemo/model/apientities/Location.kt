package com.musicdemo.model.apientities


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "country")
    val country: String?,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "localtime")
    val localtime: String?,
    @Json(name = "localtime_epoch")
    val localtimeEpoch: Int,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "name")
    val name: String?,
    @Json(name = "region")
    val region: String?,
    @Json(name = "tz_id")
    val tzId: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(country)
        parcel.writeDouble(lat)
        parcel.writeString(localtime)
        parcel.writeInt(localtimeEpoch)
        parcel.writeDouble(lon)
        parcel.writeString(name)
        parcel.writeString(region)
        parcel.writeString(tzId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}