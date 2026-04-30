package com.example.tugasweek5

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    // Deklarasi Variabel Komponen UI
    private lateinit var layoutNama: TextInputLayout
    private lateinit var editNama: TextInputEditText
    private lateinit var layoutEmail: TextInputLayout
    private lateinit var editEmail: TextInputEditText
    private lateinit var layoutPass: TextInputLayout
    private lateinit var editPass: TextInputEditText
    private lateinit var layoutConfirm: TextInputLayout
    private lateinit var editConfirm: TextInputEditText

    private lateinit var radioGroupJK: RadioGroup
    private lateinit var cbMembaca: CheckBox
    private lateinit var cbOlahraga: CheckBox
    private lateinit var cbMusik: CheckBox
    private lateinit var spinnerPekerjaan: Spinner
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Hubungkan ID dari XML ke Kotlin
        inisialisasiView()

        // 2. Setup Data Spinner (Poin 04)
        setupSpinner()

        // 3. Advanced Validation secara Real-time (Poin 02)
        setupRealTimeValidation()

        // 4. Aksi Klik Tombol Submit & Alert Dialog (Poin 04)
        btnSubmit.setOnClickListener {
            if (cekSemuaValidasi()) {
                tampilkanDialogKonfirmasi() // Muncul jika tidak ada error
            } else {
                Toast.makeText(this, "Mohon perbaiki data yang error / belum lengkap", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Gesture Interaction - Long Press pada Button (Poin 05)
        btnSubmit.setOnLongClickListener {
            Toast.makeText(this, "Aksi Long Press: Form di-reset!", Toast.LENGTH_LONG).show()
            resetForm() // Memanggil fungsi untuk mengosongkan form
            true // Wajib mengembalikan true agar klik biasa tidak ikut terpanggil
        }
    }

    private fun inisialisasiView() {
        // Menyambungkan variabel dengan ID yang ada di file XML
        layoutNama = findViewById(R.id.layoutNama)
        editNama = findViewById(R.id.editNama)
        layoutEmail = findViewById(R.id.layoutEmail)
        editEmail = findViewById(R.id.editEmail)
        layoutPass = findViewById(R.id.layoutPass)
        editPass = findViewById(R.id.editPass)
        layoutConfirm = findViewById(R.id.layoutConfirm)
        editConfirm = findViewById(R.id.editConfirm)

        radioGroupJK = findViewById(R.id.radioGroupJK)
        cbMembaca = findViewById(R.id.cbMembaca)
        cbOlahraga = findViewById(R.id.cbOlahraga)
        cbMusik = findViewById(R.id.cbMusik)
        spinnerPekerjaan = findViewById(R.id.spinnerPekerjaan)
        btnSubmit = findViewById(R.id.btnSubmit)
    }

    private fun setupSpinner() {
        val daftarPekerjaan = arrayOf("Pilih Pekerjaan...", "Mahasiswa", "Programmer", "Desainer", "Lainnya")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, daftarPekerjaan)
        spinnerPekerjaan.adapter = adapter
    }

    private fun setupRealTimeValidation() {
        // Validasi Nama: Peringatan muncul jika nama dihapus hingga kosong
        editNama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isEmpty()) {
                    layoutNama.error = "Nama tidak boleh kosong"
                } else {
                    layoutNama.error = null // Menghilangkan pesan error
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validasi Email: Cek menggunakan format email standar Android
        editEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                if (email.isEmpty()) {
                    layoutEmail.error = "Email tidak boleh kosong"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    layoutEmail.error = "Format email tidak valid (contoh: user@mail.com)"
                } else {
                    layoutEmail.error = null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validasi Password Match: Bandingkan password dan confirm password
        editConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pass = editPass.text.toString()
                val confirm = s.toString()
                if (confirm != pass) {
                    layoutConfirm.error = "Password tidak cocok!"
                } else {
                    layoutConfirm.error = null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun cekSemuaValidasi(): Boolean {
        var isValid = true

        // 1. Cek Input Form
        if (editNama.text.toString().trim().isEmpty()) { layoutNama.error = "Nama tidak boleh kosong"; isValid = false }
        if (editEmail.text.toString().trim().isEmpty() || layoutEmail.error != null) { isValid = false }
        if (editPass.text.toString().isEmpty()) { layoutPass.error = "Password tidak boleh kosong"; isValid = false }
        if (editConfirm.text.toString() != editPass.text.toString() || editConfirm.text.toString().isEmpty()) { isValid = false }

        // 2. Validasi RadioGroup (Poin 03)
        if (radioGroupJK.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // 3. Validasi Checkbox Hobi (Minimal 1 harus dicentang)
        if (!cbMembaca.isChecked && !cbOlahraga.isChecked && !cbMusik.isChecked) {
            Toast.makeText(this, "Pilih minimal 1 hobi", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // 4. Validasi Spinner (Pastikan tidak memilih item pertama)
        if (spinnerPekerjaan.selectedItemPosition == 0) {
            Toast.makeText(this, "Silakan pilih pekerjaan", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun tampilkanDialogKonfirmasi() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Pendaftaran")
        builder.setMessage("Apakah data yang Anda masukkan sudah benar?")

        // Tombol Ya
        builder.setPositiveButton("Ya, Submit") { dialog, _ ->
            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_LONG).show()
            dialog.dismiss()

            // --- INI ADALAH 3 BARIS KODE YANG BARU DITAMBAHKAN ---
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            // ---------------------------------------------------
        }

        // Tombol Batal
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun resetForm() {
        // Fungsi ini dipanggil saat Long Press (Poin 05)
        editNama.text?.clear()
        editEmail.text?.clear()
        editPass.text?.clear()
        editConfirm.text?.clear()
        radioGroupJK.clearCheck()
        cbMembaca.isChecked = false
        cbOlahraga.isChecked = false
        cbMusik.isChecked = false
        spinnerPekerjaan.setSelection(0)

        // Bersihkan status error jika ada
        layoutNama.error = null
        layoutEmail.error = null
        layoutPass.error = null
        layoutConfirm.error = null
    }
}