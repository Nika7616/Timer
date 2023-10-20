package com.example.timer.presentation.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.timer.R
import com.example.timer.presentation.Screens.Home.HomeViewState
import com.example.timer.presentation.Screens.Timer.TrainingViewModel

class timerFragment : Fragment() {
    lateinit var timeTv: TextView
    lateinit var typeTrainingTv: TextView
    lateinit var setsTv: TextView
    private val viewModel: TrainingViewModel by activityViewModels()
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var progressBar: ProgressBar
    var progressMax = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initialization(view)
        observes()

    }

    fun initialization (view: View) {
        timeTv = view.findViewById(R.id.timeTv)
        typeTrainingTv = view.findViewById(R.id.typeTrainingTv)
        setsTv = view.findViewById(R.id.setsTv)

        startBtn = view.findViewById(R.id.startBtn)
        stopBtn = view.findViewById(R.id.stoptBtn)


        progressBar = view.findViewById(R.id.progressBar2)
    }

    fun observes () {
        viewModel.progressMaxLD.observe(viewLifecycleOwner) {
            progressBar.max = it
            progressMax = it
        }

        viewModel.currentTimeLD.observe(viewLifecycleOwner) {
            timeTv.text = viewModel.millToMinSekSingleLine(it)


            progressBar.setProgress(progressMax - (it/1000).toInt())
        }

        viewModel.sets.observe(viewLifecycleOwner) {
            setsTv.text = "осталось сетов: ${it}"
        }

        viewModel.currentTypeLD.observe(viewLifecycleOwner) {
            typeTrainingTv.text = it
            if (it.equals("подготовка")) {
                typeTrainingTv.setTextColor(getResources().getColor(R.color.yellow))
                progressBar.setBackgroundColor(getResources().getColor(R.color.yellow))
            }
            if (it.equals("работа")) {
                typeTrainingTv.setTextColor(getResources().getColor(R.color.red))
                progressBar.setBackgroundColor(getResources().getColor(R.color.red))
            }
            if (it.equals("отдых")) {
                typeTrainingTv.setTextColor(getResources().getColor(R.color.green))
                progressBar.setBackgroundColor(getResources().getColor(R.color.green))
            }
        }


        viewModel.visible.observe(viewLifecycleOwner) {
            when (it) {
                HomeViewState.notVisibleStop -> {
                    startBtn.isVisible = true
                    stopBtn.isVisible = false

                }

                HomeViewState.notVisibleStart -> {
                    startBtn.isVisible = false
                    stopBtn.isVisible = true
                }

                else -> {

                }
            }


            startBtn.setOnClickListener {
                viewModel.startTime()
            }

            stopBtn.setOnClickListener {
                viewModel.stopTimer()
            }


        }
    }

}