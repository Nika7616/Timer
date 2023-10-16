package com.example.timer.presentation.Screens.AddTraining

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.timer.R
import com.example.timer.data.dataBaseSource.TrainingModel


class AddTrainingFragment : Fragment() {
    lateinit var etName: EditText

    lateinit var etPreparationMin: EditText
    lateinit var etPreparationSec: EditText

    lateinit var etTrainingMin: EditText
    lateinit var etTrainingSec: EditText

    lateinit var etRestMin: EditText
    lateinit var etRestSec: EditText

    lateinit var etSets: EditText

    private val viewModel: AddTrainingViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_training, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAdd = view.findViewById<Button>(R.id.AddBtn)

        etName = view.findViewById(R.id.EtName)

        etPreparationSec = view.findViewById(R.id.etPreparationSec)
        etTrainingSec = view.findViewById(R.id.EtTrainingSec)
        etRestSec = view.findViewById(R.id.etRestSec)


        etPreparationMin = view.findViewById(R.id.etPreparationMin)
        etTrainingMin = view.findViewById(R.id.etTrainingMin)
        etRestMin = view.findViewById(R.id.etRestMin)

        etSets = view.findViewById(R.id.EtSets)

        textChangedListener(etPreparationMin)
        textChangedListener(etPreparationSec)
        textChangedListener(etTrainingMin)
        textChangedListener(etTrainingSec)
        textChangedListener(etRestMin)
        textChangedListener(etRestSec)


        buttonAdd.setOnClickListener {

            var preparationMin = 0
            var preparationSec = 0

            var trainingMin = 0
            var trainingSec = 0

            var restMin = 0
            var restSec = 0

            try {

                preparationMin = Integer.parseInt(etPreparationMin.text.toString())
                preparationSec = Integer.parseInt(etPreparationSec.text.toString())

                trainingMin = Integer.parseInt(etTrainingMin.text.toString())
                trainingSec = Integer.parseInt(etTrainingSec.text.toString())


                restMin = Integer.parseInt(etRestMin.text.toString())
                restSec = Integer.parseInt(etRestSec.text.toString())


            } catch (e: Exception) {

            }


            val list = TrainingModel(
                name = etName.text.toString(),
                preparation = viewModel.timeMinSecToMillisec(preparationMin, preparationSec),
                training = viewModel.timeMinSecToMillisec(trainingMin, trainingSec),
                rest = viewModel.timeMinSecToMillisec(restMin, restSec),
                sets = etSets.text.toString().toInt()
            )

            viewModel.addItem(list)
            findNavController().navigate(R.id.action_addTrainingFragment_to_homeFragment)

        }

    }

    fun textChangedListener (editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 0) {
                    val currentLatterIndex = s.length - 1
                    if (currentLatterIndex == 0 && s.toString().toInt() > 5) {

                        editText.setText("0${s}")
                        editText.setSelection(editText.text.length);

                    }
                }

            }
        })

    }
}