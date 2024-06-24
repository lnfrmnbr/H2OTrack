package com.example.h2otrack.fragments

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.h2otrack.R

class WaterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_water, container, false)
    }

    override fun onResume() {
        super.onResume()
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
                    littersAtDay.text = (littersAtDay.text.toString().toInt() + numOfLitters).toString()
                }
                else{
                    Toast.makeText(requireContext(), "Выберете объем выпитой жидкости", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}