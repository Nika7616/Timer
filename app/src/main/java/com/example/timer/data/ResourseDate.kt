package com.example.timer.data

import com.example.timer.data.dataBaseSource.TrainingModel

class ResourseDate {
    val training1 = TrainingModel(id = 0, name = "дыхание", preparation = 10000, training = 10000, rest = 4000, sets = 2)

    val training2 = TrainingModel(id = 1, name = "бокс", preparation = 6000, training = 5000, rest = 5000, sets = 5)

    val training3 = TrainingModel(id = 2, name = "скакалка", preparation = 6000, training = 5000, rest = 5000, sets = 5)

    val trainings = mutableListOf(training1, training2, training3)
}