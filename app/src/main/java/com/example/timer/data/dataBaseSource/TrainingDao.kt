package com.example.timer.data.dataBaseSource

import androidx.room.*

@Dao
interface TrainingDao {
    @Query("SELECT * FROM trainings")
    fun getAllTrainings(): List<TrainingModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTraining(training: TrainingModel)

    @Delete
    fun delTraining(training: TrainingModel)

    @Update()
    fun updateTraining(training: TrainingModel)
}