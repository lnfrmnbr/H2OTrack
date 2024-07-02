package com.example.h2otrack.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.core.content.ContextCompat
import com.example.h2otrack.DBHelper
import com.example.h2otrack.R
import com.example.h2otrack.databinding.ActivityAppBinding
import com.example.h2otrack.databinding.FragmentWaterBinding
import java.util.Calendar
import kotlin.math.abs

class WaterFragment : Fragment() {

    private var _binding: FragmentWaterBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance(id: String): WaterFragment {
            val fragment = WaterFragment()
            val args = Bundle()
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }

        private val animationDuration = 1000L

        private var barSet = mutableListOf(
            "24.6" to 0F,
            "25.6" to 0F,
            "26.6" to 0F,
            "27.6" to 0F,
            "28.6" to 0F,
            "29.6" to 0F,
            "29.6" to 0F,
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_water, container, false)
    }

    override fun onResume() {
        super.onResume()

        val littersAtDay: TextView = requireView().findViewById(R.id.litters_at_day)
        val id = arguments?.get("id").toString().toInt()
        val db = DBHelper(requireContext(), null)
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val data = "$day.$month"

        if(!(db.checkDataForDay(id, day, month))){
            db.addDataForDay(id, day, month)
        }

        if(!(barSet.any { it.first == data })){
            for (i in 0..6) {
                val _calendar = Calendar.getInstance()
                _calendar.add(Calendar.DAY_OF_YEAR, -i)
                val _day = _calendar.get(Calendar.DAY_OF_MONTH)
                val _month = _calendar.get(Calendar.MONTH) + 1
                barSet[abs(i-6)] = "$_day.$_month" to db.getCurrentMlOfDay(id, _day, _month).toFloat()
            }
        }

        val i = barSet.indexOfFirst{ it.first == data }
        barSet[i] = data to db.getCurrentMlOfDay(id, day, month).toFloat()
        littersAtDay.text = db.getCurrentMlOfDay(id, day, month).toString()

        val waterBarChart = requireView().findViewById<com.db.williamchart.view.BarChartView>(R.id.water_barChart)
        waterBarChart.animation.duration = animationDuration
        waterBarChart.animate(barSet)

        val buttPlus: Button = requireView().findViewById(R.id.plus)

        buttPlus.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.select_container, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            val buttGlass = dialogBinding.findViewById<ImageButton>(R.id.butt_glass)
            val buttCup = dialogBinding.findViewById<ImageButton>(R.id.butt_cup)
            val buttBottle = dialogBinding.findViewById<ImageButton>(R.id.butt_bottle)
            val buttUnknown = dialogBinding.findViewById<ImageButton>(R.id.butt_unknown)
            var numOfLitters = 0
            var isUnknownSelected = false

            buttGlass.setOnClickListener {
                buttGlass.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue_dark
                    )
                )
                buttCup.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttBottle.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttUnknown.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                numOfLitters = 200
                isUnknownSelected = false
            }

            buttCup.setOnClickListener {
                buttGlass.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttCup.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_dark))
                buttBottle.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttUnknown.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                numOfLitters = 300
                isUnknownSelected = false
            }

            buttBottle.setOnClickListener {
                buttGlass.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttCup.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttBottle.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue_dark
                    )
                )
                buttUnknown.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                numOfLitters = 500
                isUnknownSelected = false
            }

            buttUnknown.setOnClickListener {
                buttGlass.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttCup.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttBottle.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                buttUnknown.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_dark))
                numOfLitters = 0
                isUnknownSelected = true
            }

            val buttConfirm = dialogBinding.findViewById<Button>(R.id.butt_select_container)
            buttConfirm.setOnClickListener {
                val getNumOfLitters = dialogBinding.findViewById<EditText>(R.id.num_of_ml).text.toString()
                if(isUnknownSelected){
                    if (getNumOfLitters != "") {
                        numOfLitters = getNumOfLitters.toInt()
                    }
                }
                if (numOfLitters != 0 || (isUnknownSelected && getNumOfLitters == "0")){
                    myDialog.dismiss()
                    val littersAtDay: TextView = requireView().findViewById(R.id.litters_at_day)
                    db.changeWaterValue(id, day, month, numOfLitters)
                    littersAtDay.text = db.getCurrentMlOfDay(id, day, month).toString()
                    val j = barSet.indexOfFirst{ it.first == data }
                    barSet[j] = data to db.getCurrentMlOfDay(id, day, month).toFloat()
                    waterBarChart.animate(barSet)
                }
                else{
                    Toast.makeText(requireContext(), "Выберете объем выпитой жидкости", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}