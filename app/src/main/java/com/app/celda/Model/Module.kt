package com.app.celda.Model

import android.os.Parcel
import android.os.Parcelable

data class Module (
    var nameModule : String,
    var lessons : List<Lesson>   //TODO: Удалить этц парашу и превратить в лист
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        listOf<Lesson>()
    )

    val lst : List<Lesson> = ArrayList<Lesson>()


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nameModule)
        for(element in lst) {
            parcel.writeString(element.toString())
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Module> {
        override fun createFromParcel(parcel: Parcel): Module {
            return Module(parcel)
        }

        override fun newArray(size: Int): Array<Module?> {
            return arrayOfNulls(size)
        }
    }

}