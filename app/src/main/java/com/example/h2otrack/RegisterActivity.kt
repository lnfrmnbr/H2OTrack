package com.example.h2otrack

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.h2otrack.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sexes = resources.getStringArray(R.array.sex)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, sexes)
        binding.regSex.setAdapter(arrayAdapter)

        val regName: TextInputEditText = findViewById<TextInputEditText>(R.id.reg_name)
        val regSex: AutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.reg_sex)
        val regWeight: TextInputEditText = findViewById<TextInputEditText>(R.id.reg_weight)
        val regActivity: TextInputEditText = findViewById<TextInputEditText>(R.id.reg_activity)
        val regPassword: TextInputEditText = findViewById<TextInputEditText>(R.id.reg_password)
        val regButton: Button = findViewById<Button>(R.id.button_register)

        regButton.setOnClickListener {
            val name = regName.text.toString().trim()
            val sex = regSex.text.toString().trim()
            val weight = regWeight.text.toString().trim()
            val activity = regActivity.text.toString().trim()
            val pass = regPassword.text.toString().trim()

            if(name == "" || sex == "" || weight == "" || activity == "" || pass == ""){
                Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_LONG).show()
            }
            else{
                val weightInt = weight.toInt()
                val activityInt = activity.toInt()
                val user = User(name, sex, weightInt, activityInt, pass)

                val db = DBHelper(this, null)
                db.addUser(user)

                regName.text?.clear()
                regSex.text?.clear()
                regWeight.text?.clear()
                regActivity.text?.clear()
                regPassword.text?.clear()
            }
        }



        val buttonBackToMain = findViewById<ImageButton>(R.id.back_to_main)

        buttonBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}