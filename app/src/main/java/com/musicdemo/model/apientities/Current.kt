package com.musicdemo.model.apientities


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Current(
    @Json(name = "cloud")
    val cloud: Int,
    @Json(name = "condition")
    val condition: Condition?,
    @Json(name = "feelslike_c")
    val feelslikeC: Double,
    @Json(name = "feelslike_f")
    val feelslikeF: Double,
    @Json(name = "gust_kph")
    val gustKph: Double,
    @Json(name = "gust_mph")
    val gustMph: Double,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "is_day")
    val isDay: Int,
    @Json(name = "last_updated")
    val lastUpdated: String?,
    @Json(name = "last_updated_epoch")
    val lastUpdatedEpoch: Int,
    @Json(name = "precip_in")
    val precipIn: Double,
    @Json(name = "precip_mm")
    val precipMm: Double,
    @Json(name = "pressure_in")
    val pressureIn: Double,
    @Json(name = "pressure_mb")
    val pressureMb: Double,
    @Json(name = "temp_c")
    val tempC: Double,
    @Json(name = "temp_f")
    val tempF: Double,
    @Json(name = "uv")
    val uv: Double,
    @Json(name = "vis_km")
    val visKm: Double,
    @Json(name = "vis_miles")
    val visMiles: Double,
    @Json(name = "wind_degree")
    val windDegree: Int,
    @Json(name = "wind_dir")
    val windDir: String?,
    @Json(name = "wind_kph")
    val windKph: Double,
    @Json(name = "wind_mph")
    val windMph: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Condition::class.java.classLoader),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cloud)
        parcel.writeParcelable(condition, flags)
        parcel.writeDouble(feelslikeC)
        parcel.writeDouble(feelslikeF)
        parcel.writeDouble(gustKph)
        parcel.writeDouble(gustMph)
        parcel.writeInt(humidity)
        parcel.writeInt(isDay)
        parcel.writeString(lastUpdated)
        parcel.writeInt(lastUpdatedEpoch)
        parcel.writeDouble(precipIn)
        parcel.writeDouble(precipMm)
        parcel.writeDouble(pressureIn)
        parcel.writeDouble(pressureMb)
        parcel.writeDouble(tempC)
        parcel.writeDouble(tempF)
        parcel.writeDouble(uv)
        parcel.writeDouble(visKm)
        parcel.writeDouble(visMiles)
        parcel.writeInt(windDegree)
        parcel.writeString(windDir)
        parcel.writeDouble(windKph)
        parcel.writeDouble(windMph)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Current> {
        override fun createFromParcel(parcel: Parcel): Current {
            return Current(parcel)
        }

        override fun newArray(size: Int): Array<Current?> {
            return arrayOfNulls(size)
        }
    }
}