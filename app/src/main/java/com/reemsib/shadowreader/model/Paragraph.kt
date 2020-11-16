package com.reemsib.shadowreader.model

import android.os.Parcel
import android.os.Parcelable
//,var is_watched:Boolean,var is_send_voice:Boolean

data class Paragraph(var id: Int, var title: String?, var video_path:String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()!!,
      //  parcel.readBoolean(),
      //  parcel.readBoolean()
     )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(video_path)
    //    parcel.writeBoolean(is_watched)
     //   parcel.writeBoolean(is_send_voice)
        }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Paragraph> {
        const val TABLE_NAME = "Paragraph"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_VIDEO = "video"
        const val COL_ID_LESSON= "id_lesson"

        const val CREATE_TABLE =
            "create table $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NAME TEXT NOT NULL, $COL_VIDEO TEXT NOT NULL," +
                    "$COL_ID_LESSON INTEGER REFERENCES ${Lesson.TABLE_NAME}(${Lesson.COL_ID}))"
        override fun createFromParcel(parcel: Parcel): Paragraph {
            return Paragraph(parcel)
        }

        override fun newArray(size: Int): Array<Paragraph?> {
            return arrayOfNulls(size)
        }
    }
}
