package com.example.timer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timer.data.dataBaseSource.TrainingDao
import com.example.timer.data.dataBaseSource.TrainingModel
import com.example.timer.data.dataBaseSource.www

@Database(entities = [TrainingModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getTrainingDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            val instance  = Room.databaseBuilder(
                context, AppDatabase::class.java,
                "training-database"
            ).build()
            INSTANCE = instance
            instance
        }

    }

}