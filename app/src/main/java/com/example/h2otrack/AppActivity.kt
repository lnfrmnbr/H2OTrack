package com.example.h2otrack

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.h2otrack.fragments.ProfileFragment
import com.example.h2otrack.fragments.TotalFragment
import com.example.h2otrack.fragments.WaterFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class AppActivity : AppCompatActivity() {
    var userId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Все напитки", R.drawable.mug_hot_solid)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Вода", R.drawable.glass_water_solid)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Профиль", R.drawable.user_solid)
        )
        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> {
                    replaceFragment(TotalFragment())
                }
                2 -> {
                    openWaterFragmentWithUserId()
                }
                3 -> {
                    replaceFragment(ProfileFragment())
                }
            }
        }
        openWaterFragmentWithUserId()
        bottomNavigation.show(2)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("DEBUG", "onActivityResult of appact")
        super.onActivityResult(requestCode, resultCode, data)
        val message = intent.getStringExtra("id")
        if (message != null) {
            userId = message.toString()
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun openWaterFragmentWithUserId() {
        val message = intent.getStringExtra("id")
        Log.e("DEBUG", "openWaterFragmentWithUserId mess '$message'")
        if (message != null) {
            userId = message.toString()
        }
        Log.e("DEBUG", "openWaterFragmentWithUserId '$userId'")
        val waterFragment = WaterFragment.newInstance(userId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, waterFragment)
            .commit()
    }
}