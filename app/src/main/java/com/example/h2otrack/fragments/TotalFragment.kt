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
import com.db.williamchart.view.BarChartView
import com.example.h2otrack.R

class TotalFragment : Fragment() {

    companion object {
        const val animationDuration = 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onResume() {
        super.onResume()

        val waterProgress = requireView().findViewById<ProgressBar>(R.id.water_progress)
        waterProgress.max = 1000
        ObjectAnimator.ofInt(waterProgress, "progress", 300)
            .setDuration(1000)
            .start()

    }

}