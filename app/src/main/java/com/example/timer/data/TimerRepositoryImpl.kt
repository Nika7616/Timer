package com.example.timer.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.timer.data.dataBaseSource.TrainingModel
import com.example.timer.data.dataBaseSource.www
import com.example.timer.domain.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TimerRepositoryImpl(val context: Context) : TrainingRepository {

    val trainingListLD = MutableLiveData<List<TrainingModel>>()

    val resourseDate: ResourseDate = ResourseDate()
    val trainingList = resourseDate.trainings

    val db = AppDatabase.getTrainingDatabase(context)

    var currentId = 3



    init {


    }


    override suspend fun getTrainingList(): MutableLiveData<List<TrainingModel>> {
        return withContext(Dispatchers.IO) {
            updateData()
            trainingListLD
        }
    }

    override suspend fun deleteItem(item: TrainingModel) {
        withContext(Dispatchers.IO) {
            db.trainingDao().delTraining(item)
            updateData()
        }
    }

    override suspend fun editItem(newElement: TrainingModel) {
        withContext(Dispatchers.IO) {
            //        var oldElement = newElement.id?.let { getElementById(it) }
//        oldElement?.let { deleteItem(it) }
//        addItem(newElement)
            db.trainingDao().updateTraining(newElement)
            updateData()
        }


    }


    suspend fun addItem(trainingModel: TrainingModel) {
        withContext(Dispatchers.IO) {

//            if (trainingModel.id == TrainingModel.ID_NOT_EXSIST) {
//                trainingModel.id = ++currentId
//            }

            db.trainingDao().insertTraining(trainingModel)
            updateData()

        }


    }


    fun updateData() {
        trainingListLD.postValue(db.trainingDao().getAllTrainings())

    }

    fun getElementById(id: Int): TrainingModel? {
        for (element in trainingList) {
            if (element.id == id) {
                return element
            }
        }

        return null

    }
}