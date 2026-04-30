package com.example.tugasweek5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resNama = findViewById<TextView>(R.id.resNama)
        val resEmail = findViewById<TextView>(R.id.resEmail)
        val resHp = findViewById<TextView>(R.id.resHp)
        val resGender = findViewById<TextView>(R.id.resGender)
        val resSeminar = findViewById<TextView>(R.id.resSeminar)
        val btnBackToHome = findViewById<Button>(R.id.btnBackToHome)

        val nama = intent.getStringExtra("EXTRA_NAMA")
        val email = intent.getStringExtra("EXTRA_EMAIL")
        val hp = intent.getStringExtra("EXTRA_HP")
        val gender = intent.getStringExtra("EXTRA_GENDER")
        val seminar = intent.getStringExtra("EXTRA_SEMINAR")

        resNama.text = "Nama: $nama"
        resEmail.text = "Email: $email"
        resHp.text = "No. HP: $hp"
        resGender.text = "Gender: $gender"
        resSeminar.text = "Seminar: $seminar"

        btnBackToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}