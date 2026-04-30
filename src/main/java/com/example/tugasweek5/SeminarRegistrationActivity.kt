package com.example.tugasweek5

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SeminarRegistrationActivity : AppCompatActivity() {

    private lateinit var layoutRegEmail: TextInputLayout
    private lateinit var editRegEmail: TextInputEditText
    private lateinit var layoutRegHp: TextInputLayout
    private lateinit var editRegHp: TextInputEditText
    private lateinit var editRegNama: TextInputEditText
    private lateinit var rgGender: RadioGroup
    private lateinit var spinnerSeminar: Spinner
    private lateinit var cbPersetujuan: CheckBox
    private lateinit var btnSubmitSeminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminar_registration)

        initViews()
        setupSpinner()
        setupRealTimeValidation()

        btnSubmitSeminar.setOnClickListener {
            if (validateAllInput()) {
                showConfirmationDialog()
            }
        }
    }

    private fun initViews() {
        layoutRegEmail = findViewById(R.id.layoutRegEmail)
        editRegEmail = findViewById(R.id.editRegEmail)
        layoutRegHp = findViewById(R.id.layoutRegHp)
        editRegHp = findViewById(R.id.editRegHp)
        editRegNama = findViewById(R.id.editRegNama)
        rgGender = findViewById(R.id.rgGender)
        spinnerSeminar = findViewById(R.id.spinnerSeminar)
        cbPersetujuan = findViewById(R.id.cbPersetujuan)
        btnSubmitSeminar = findViewById(R.id.btnSubmitSeminar)
    }

    private fun setupSpinner() {
        val listSeminar = arrayOf("Pilih Seminar...", "Seminar AI & Machine Learning", "Seminar Mobile Development", "Seminar Cyber Security", "Seminar Data Science", "Seminar UI/UX Design")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listSeminar)
        spinnerSeminar.adapter = adapter
    }

    private fun setupRealTimeValidation() {
        editRegEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().contains("@")) {
                    layoutRegEmail.error = "Email harus mengandung '@'"
                } else {
                    layoutRegEmail.error = null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        editRegHp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val hp = s.toString()
                if (hp.isNotEmpty()) {
                    if (!hp.matches(Regex("^[0-9]+$"))) {
                        layoutRegHp.error = "Hanya boleh angka"
                    } else if (!hp.startsWith("08")) {
                        layoutRegHp.error = "Harus diawali dengan 08"
                    } else if (hp.length !in 10..13) {
                        layoutRegHp.error = "Panjang harus 10-13 digit"
                    } else {
                        layoutRegHp.error = null
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateAllInput(): Boolean {
        var isValid = true

        if (editRegNama.text.toString().trim().isEmpty()) {
            editRegNama.error = "Nama wajib diisi"
            isValid = false
        }
        if (layoutRegEmail.error != null || editRegEmail.text.toString().isEmpty()) {
            Toast.makeText(this, "Periksa kembali input Email", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (layoutRegHp.error != null || editRegHp.text.toString().isEmpty()) {
            Toast.makeText(this, "Periksa kembali input Nomor HP", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (spinnerSeminar.selectedItemPosition == 0) {
            Toast.makeText(this, "Silakan pilih seminar", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (!cbPersetujuan.isChecked) {
            Toast.makeText(this, "Anda harus menyetujui data yang diinput benar", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah data yang Anda isi sudah benar?")

        builder.setPositiveButton("Ya") { _, _ ->
            val selectedRbId = rgGender.checkedRadioButtonId
            val rbGender = findViewById<RadioButton>(selectedRbId)

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("EXTRA_NAMA", editRegNama.text.toString())
                putExtra("EXTRA_EMAIL", editRegEmail.text.toString())
                putExtra("EXTRA_HP", editRegHp.text.toString())
                putExtra("EXTRA_GENDER", rbGender.text.toString())
                putExtra("EXTRA_SEMINAR", spinnerSeminar.selectedItem.toString())
            }
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}