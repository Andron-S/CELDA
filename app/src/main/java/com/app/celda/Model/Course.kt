package com.app.celda.Model

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class Course(
    var id : Int,
    var img : Bitmap?,
    var name : String,
    var description : String,
    var author : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(img, flags)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(author)
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}