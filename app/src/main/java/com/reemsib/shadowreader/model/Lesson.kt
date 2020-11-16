package com.reemsib.shadowreader.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

data class Lesson(var id: Int, var title: String?,var id_semester:Int) : Parcelable {

    @SuppressLint("NewApi")
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
        //  parcel.readBoolean()

    )override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeInt(id_semester)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            //parcel.writeBoolean(isComplete)
        }
    }
    companion object CREATOR : Parcelable.Creator<Lesson> {
        const val TABLE_NAME = "Lesson"
        const val COL_ID = "id"
        const val COL_NAME = "name"
         const val COL_ID_SEMESTER = "id_semester"

        const val CREATE_TABLE =
            "create table $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NAME TEXT NOT NULL," +
                    "$COL_ID_SEMESTER INTEGER REFERENCES ${Semester.TABLE_NAME}(${Semester.COL_ID}))"

        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

}
