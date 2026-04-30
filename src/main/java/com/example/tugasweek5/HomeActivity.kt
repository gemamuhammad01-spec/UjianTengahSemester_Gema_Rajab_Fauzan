package com.example.tugasweek5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        // Tombol ini akan membawa kita ke halaman form pendaftaran
        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, SeminarRegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}