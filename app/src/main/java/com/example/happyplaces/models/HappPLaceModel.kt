package com.example.happyplaces.models

import android.os.Parcel
import android.os.Parcelable

data class HappyPlaceModel(
    val id:Int,
    val title:String?,
    val Image:String?,
    val Description:String?,
    val Date:String?,
    val Location:String?,
    val Latitude:Double,
    val Longitude:Double
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(Image)
        parcel.writeString(Description)
        parcel.writeString(Date)
        parcel.writeString(Location)
        parcel.writeDouble(Latitude)
        parcel.writeDouble(Longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HappyPlaceModel> {
        override fun createFromParcel(parcel: Parcel): HappyPlaceModel {
            return HappyPlaceModel(parcel)
        }

        override fun newArray(size: Int): Array<HappyPlaceModel?> {
            return arrayOfNulls(size)
        }
    }
}