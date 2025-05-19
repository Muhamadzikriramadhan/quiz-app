package id.co.androdevjik.quizapp.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.co.androdevjik.quizapp.R
import id.co.androdevjik.quizapp.database.QuizDbHelper

class RegisterActivity : AppCompatActivity() {
    private lateinit var dbHelper: QuizDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = QuizDbHelper(this)

        val roles = listOf("student", "teacher")
        val spinner = findViewById<Spinner>(R.id.spinnerRole)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            val name = findViewById<EditText>(R.id.etName).text.toString()
            val role = findViewById<Spinner>(R.id.spinnerRole).selectedItem.toString()

            val success = dbHelper.registerUser(username, password, name, role)
            if (success) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show()
            }
        }
    }
}