package com.example.timer.presentation.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.presentation.Screens.EditTraining.EditTrainingFragment
import com.example.timer.presentation.Screens.EditTraining.EditTrainingViewModel
import com.example.timer.presentation.Screens.Home.HomeViewModel
import com.example.timer.presentation.Screens.Home.TrainingAdapter
import com.example.timer.presentation.Screens.Home.HomeViewState
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val viewModelEdit: EditTrainingViewModel by activityViewModels()
    private lateinit var trainingAdapter: TrainingAdapter
    private lateinit var rv_training: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var buttonAddItem: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialization(view)

        setObservers()

        setupOnClickListener()
        setupSwipeListener(rv_training)
        setupClickListener()
        setupLongClickListener()

    }

    fun initialization(view: View) {
        progressBar = view.findViewById(R.id.progressBar)

        textView = view.findViewById(R.id.textView)

        trainingAdapter = TrainingAdapter()

        buttonAddItem = view.findViewById(R.id.floatingActionButton)

        rv_training = view.findViewById(R.id.recyclerview)
    }

    fun setObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                HomeViewState.enabled -> {
                    progressBar.isVisible = false
                    buttonAddItem.isClickable = true
                    rv_training.isClickable = true


                }

                HomeViewState.disabled -> {
                    progressBar.isVisible = true
                    buttonAddItem.isClickable = false
                    rv_training.isClickable = false
                }

                else -> {

                }
            }

            viewModel.trainingListLD.observe(viewLifecycleOwner) {
                trainingAdapter.trainingList = it
                rv_training.adapter = trainingAdapter
            }

        }
    }


    private fun setupOnClickListener () {
        buttonAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTrainingFragment)

        }
    }

    private fun setupSwipeListener(rvList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = trainingAdapter.trainingList[viewHolder.adapterPosition]
                viewModel.deleteItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvList)
    }

    private fun setupClickListener() {
        trainingAdapter.onItemClickListener = {
            viewModel.setCurrentTraining(it)
            findNavController().navigate(R.id.action_homeFragment_to_timerFragment)

        }
    }

    private fun setupLongClickListener() {
        trainingAdapter.onItemLongClickListener = {
         viewModelEdit.setCurrentTrainingForEdit(it)
            val yfc = EditTrainingFragment()
            val bundle = Bundle()
            bundle.putInt("tag", it.id)
            yfc.setArguments(bundle)
            val fragmentManager = getFragmentManager()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, yfc)?.commit()
        }
    }


}