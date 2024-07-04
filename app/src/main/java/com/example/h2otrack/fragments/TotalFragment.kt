package com.example.h2otrack.fragments

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.db.williamchart.view.BarChartView
import com.example.h2otrack.DBHelper
import com.example.h2otrack.R
import java.util.Calendar

class TotalFragment : Fragment() {

    companion object {
        fun newInstance(id: String): TotalFragment {
            val fragment = TotalFragment()
            val args = Bundle()
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }

        private const val ANIMATION_DURATION = 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onResume() {
        super.onResume()

        val numWater: TextView = requireView().findViewById(R.id.num_water)
        val numCoffee: TextView = requireView().findViewById(R.id.num_coffee)
        val numTea: TextView = requireView().findViewById(R.id.num_tea)
        val id = arguments?.get("id").toString().toInt()
        val db = DBHelper(requireContext(), null)
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1

        val waterNorm = db.getNormOfWater(id)
        val normWaterText : TextView = requireView().findViewById(R.id.norm_water)
        normWaterText.text = waterNorm.toString()

        val currentWaterLitters = db.getCurrentMlOfDay(id, day, month)/1000
        val currentCoffeeLitters = db.getCurrentCoffeeOfDay(id, day, month)
        val currentTeaLitters = db.getCurrentTeaOfDay(id, day, month)
        numWater.text = currentWaterLitters.toString()
        numCoffee.text = currentCoffeeLitters.toString()
        numTea.text = currentTeaLitters.toString()

        val waterProgress = requireView().findViewById<ProgressBar>(R.id.water_progress)
        waterProgress.max = 1000
        ObjectAnimator.ofInt(waterProgress, "progress", currentWaterLitters)
            .setDuration(ANIMATION_DURATION)
            .start()

    }
}