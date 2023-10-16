package com.example.timer.presentation.Screens.EditTraining

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.timer.R
import com.example.timer.data.dataBaseSource.TrainingModel

class EditTrainingFragment : Fragment() {
    lateinit var etNameEdit: EditText

    lateinit var etPreparationMinEdit: EditText
    lateinit var etPreparationSecEdit: EditText

    lateinit var etTrainingMinEdit: EditText
    lateinit var etTrainingSecEdit: EditText

    lateinit var etRestMinEdit: EditText
    lateinit var etRestSecEdit: EditText

    lateinit var etSetsEdit: EditText

    private val viewModel: EditTrainingViewModel by activityViewModels()
    var currentId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_training, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonEdit = view.findViewById<Button>(R.id.EditBtn)

        etNameEdit = view.findViewById(R.id.EtNameEdit)

        etPreparationSecEdit = view.findViewById(R.id.etPreparationSecEdit)
        etTrainingSecEdit = view.findViewById(R.id.EtTrainingSecEdit)
        etRestSecEdit = view.findViewById(R.id.etRestSecEdit)


        etPreparationMinEdit = view.findViewById(R.id.etPreparationMinEdit)
        etTrainingMinEdit = view.findViewById(R.id.etTrainingMinEdit)
        etRestMinEdit = view.findViewById(R.id.etRestMinEdit)

        etSetsEdit = view.findViewById(R.id.EtSetsEdit)

        viewModel.currentTrainingforEditLD.observe(viewLifecycleOwner) {
            currentId = it.id
            etNameEdit.setText(it.name)

            val timePrep = viewModel.millToMinSek(it.preparation)

            val timeTraining = viewModel.millToMinSek(it.training)

            val timeRest = viewModel.millToMinSek(it.rest)

            etPreparationMinEdit.setText(timePrep.min.toString())
            etPreparationSecEdit.setText(timePrep.sec.toString())

            etTrainingMinEdit.setText(timeTraining.min.toString())
            etTrainingSecEdit.setText(timeTraining.sec.toString())

            etRestMinEdit.setText(timeRest.min.toString())
            etRestSecEdit.setText(timeRest.sec.toString())

            etSetsEdit.setText(it.sets.toString())
        }

        textChangedListener(etPreparationMinEdit)
        textChangedListener(etPreparationSecEdit)
        textChangedListener(etTrainingMinEdit)
        textChangedListener(etTrainingSecEdit)
        textChangedListener(etRestMinEdit)
        textChangedListener(etRestSecEdit)

        buttonEdit.setOnClickListener {
            var preparationMin = 0
            var preparationSec = 0

            var trainingMin = 0
            var trainingSec = 0

            var restMin = 0
            var restSec = 0

            try {

                preparationMin = Integer.parseInt(etPreparationMinEdit.text.toString())
                preparationSec = Integer.parseInt(etPreparationSecEdit.text.toString())

                trainingMin = Integer.parseInt(etTrainingMinEdit.text.toString())
                trainingSec = Integer.parseInt(etTrainingSecEdit.text.toString())


                restMin = Integer.parseInt(etRestMinEdit.text.toString())
                restSec = Integer.parseInt(etRestSecEdit.text.toString())


            } catch (e: Exception) {

            }


            val training = TrainingModel(
                id = currentId,
                name = etNameEdit.text.toString(),
                preparation = viewModel.timeMinSecToMillisec(preparationMin, preparationSec),
                training = viewModel.timeMinSecToMillisec(trainingMin, trainingSec),
                rest = viewModel.timeMinSecToMillisec(restMin, restSec),
                sets = etSetsEdit.text.toString().toInt()
            )

            viewModel.editItem(training)
            findNavController().navigate(R.id.action_editTrainingFragment_to_homeFragment)

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