package com.comiccoder.chitramanjari.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.database.getCurrentUser

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.TRANSPARENT
        Handler(Looper.getMainLooper()).postDelayed({
            if (getCurrentUser() == null) {
                startActivity(Intent(this@MainActivity, Login::class.java))
                finish()
            } else {
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }
        }, 3000)
    }
}