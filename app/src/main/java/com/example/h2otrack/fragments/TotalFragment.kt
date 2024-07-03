package com.example.h2otrack.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.db.williamchart.view.BarChartView
import com.example.h2otrack.R

class TotalFragment : Fragment() {

    companion object {
        const val animationDuration = 1000L

        private var barSetWater = mutableListOf(
            "" to 10F,
            "" to 20F,
        )

        private var barsColorsList = mutableListOf(
            Color.parseColor("#FFA726"),
            Color.argb(0, 0, 0, 0),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onResume() {
        super.onResume()

        val waterProcess = requireView().findViewById<BarChartView>(R.id.water_progress)
        waterProcess.animation.duration = animationDuration
        waterProcess.animate(barSetWater)
        waterProcess.barsColorsList = barsColorsList

        val coffeeProcess = requireView().findViewById<BarChartView>(R.id.coffee_progress)
        coffeeProcess.animation.duration = animationDuration
        coffeeProcess.animate(barSetWater)

        val teaProcess = requireView().findViewById<BarChartView>(R.id.tea_progress)
        teaProcess.animation.duration = animationDuration
        teaProcess.animate(barSetWater)

        Log.e("DEBUG","данные добавлены ")
    }

}