package com.example.h2otrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val authName: TextInputEditText = findViewById<TextInputEditText>(R.id.auth_name)
        val authPassword: TextInputEditText = findViewById<TextInputEditText>(R.id.auth_password)
        val authButton: Button = findViewById<Button>(R.id.button_auth)

        authButton.setOnClickListener {
            val name = authName.text.toString().trim()
            val pass = authPassword.text.toString().trim()

            if(name == "" || pass == ""){
                Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_LONG).show()
            }
            else{
                val db = DBHelper(this, null)
                val isAuth = db.checkUser(name, pass)

                if(isAuth){
                    db.displayAllData()
                    val id = db.getId(name, pass)
                    authName.text?.clear()
                    authPassword.text?.clear()
                    val intentt = Intent(this, AppActivity::class.java)
                    intentt.putExtra("id", id.toString())
                    startActivity(intentt)
                }
                else{
                    Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_LONG).show()
                }
            }
        }

        val buttonBackToMain = findViewById<ImageButton>(R.id.back_to_main)

        buttonBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}