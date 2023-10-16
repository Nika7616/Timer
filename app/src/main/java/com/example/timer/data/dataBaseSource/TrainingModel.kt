package com.example.timer.data.dataBaseSource

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "trainings")
data class TrainingModel(

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = ID_NOT_EXSIST,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "preparation") val preparation: Long,

    @ColumnInfo(name = "training") val training: Long,

    @ColumnInfo(name = "rest") val rest: Long,

    @ColumnInfo(name = "sets") val sets: Int
)


{
    companion object {

        const val ID_NOT_EXSIST = 0
    }
}

