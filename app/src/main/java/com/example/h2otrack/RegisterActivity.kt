package com.example.h2otrack

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.h2otrack.databinding.ActivityRegisterBinding

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

        val buttonBackToMain = findViewById<ImageButton>(R.id.back_to_main)


        buttonBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}