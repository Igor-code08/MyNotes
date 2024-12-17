package com.example.mynotes

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Note(
    var id: Int, // Порядковый номер заметки
    var text: String, // Текст заметки
    var isCompleted: Boolean, // Статус выполнения
    var timestamp: String // Дата и время создания заметки
) :  Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(text)
        parcel.writeByte(if (isCompleted) 1 else 0)
        parcel.writeString(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}
