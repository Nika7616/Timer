package com.example.timer.data.dataBaseSource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class www (
    @PrimaryKey(autoGenerate = false) var id: Int? = ID_NOT_EXSIST,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "preparation") val preparation: Int?,
    @ColumnInfo(name = "training") val training: Int?,
    @ColumnInfo(name = "rest") val rest: Int?,
    @ColumnInfo(name = "sets") val sets: Int?
) {
    companion object {
        const val ID_NOT_EXSIST = -1
    }
}

