package com.example.h2otrack

import android.os.Bundle
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
                    replaceFragment(WaterFragment())
                }
                3 -> {
                    replaceFragment(ProfileFragment())
                }
            }
        }
        replaceFragment(WaterFragment())
        bottomNavigation.show(2)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}