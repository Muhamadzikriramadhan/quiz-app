package id.co.androdevjik.quizapp

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.co.androdevjik.quizapp.ui.QuizActivity
import id.co.androdevjik.quizapp.ui.UserListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStartQuiz).setOnClickListener {
            val input = EditText(this).apply {
                hint = "Masukkan nama"
                inputType = InputType.TYPE_CLASS_TEXT
            }

            val radioGroup = RadioGroup(this).apply {
                orientation = RadioGroup.VERTICAL
                val options = listOf(5, 10, 15, 20)
                options.forEach { value ->
                    val radio = RadioButton(this@MainActivity).apply {
                        text = "$value soal"
                        tag = value
                    }
                    addView(radio)
                }
                check(getChildAt(0).id) // default selected
            }

            val container = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(20.dpToPx(), 16.dpToPx(), 20.dpToPx(), 0)
                addView(input)
                addView(radioGroup)
            }

            MaterialAlertDialogBuilder(this)
                .setTitle("Masukkan namamu!")
                .setView(container)
                .setPositiveButton("Mulai") { _, _ ->
                    val name = input.text.toString()
                    val selectedRadioId = radioGroup.checkedRadioButtonId

                    if (name.isNotBlank() && selectedRadioId != -1) {
                        val selectedRadio = radioGroup.findViewById<RadioButton>(selectedRadioId)
                        val quizLength = selectedRadio.tag.toString().toInt()

                        val intent = Intent(this, QuizActivity::class.java)
                        intent.putExtra("name", name)
                        intent.putExtra("quizLength", quizLength)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Nama & Jumlah Quiz tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }


        findViewById<Button>(R.id.btnViewUsers).setOnClickListener {
            startActivity(Intent(this, UserListActivity::class.java))
        }
    }

    fun Int.dpToPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

}
