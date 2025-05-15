package id.co.androdevjik.quizapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.co.androdevjik.quizapp.R
import id.co.androdevjik.quizapp.database.QuizDbHelper
import id.co.androdevjik.quizapp.model.Question
import java.text.SimpleDateFormat
import java.util.Locale

class QuizActivity : AppCompatActivity() {
    private lateinit var db: QuizDbHelper
    private lateinit var questions: List<Question>
    private var index = 0
    private var score = 0
    private var startTime: Long = 0L
    private lateinit var name: String
    private var questionTotal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val name = intent.getStringExtra("name")
        val quizLength = intent.getIntExtra("quizLength", -1)

        if (name == null || quizLength == -1) {
            Toast.makeText(this, "Data intent tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        this.name = name
        this.questionTotal = quizLength

        db = QuizDbHelper(this)
        questions = db.getRandomQuestions(questionTotal)
        startTime = System.currentTimeMillis()

        showQuestion()

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            val selected = findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId
            val selectedOption = when (selected) {
                R.id.option1 -> 1
                R.id.option2 -> 2
                R.id.option3 -> 3
                R.id.option4 -> 4
                else -> 0
            }

            if (selectedOption == questions[index].answer) score++
            index++

            if (index < questions.size) {
                showQuestion()
            } else {
                val endTime = System.currentTimeMillis()
                val duration = (endTime - startTime) / 1000
                val timeNow = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                    java.util.Date()
                )
                db.insertUser(name, score, timeNow, questionTotal, "$duration detik")

                AlertDialog.Builder(this)
                    .setTitle("Selesai!")
                    .setMessage("Skor kamu: $score / $questionTotal\nWaktu: $duration detik")
                    .setPositiveButton("Kembali") { _, _ -> finish() }
                    .show()
            }
        }
    }

    private fun showQuestion() {
        val q = questions[index]
        findViewById<TextView>(R.id.tvQuestion).text = q.question
        findViewById<RadioButton>(R.id.option1).text = q.option1
        findViewById<RadioButton>(R.id.option2).text = q.option2
        findViewById<RadioButton>(R.id.option3).text = q.option3
        findViewById<RadioButton>(R.id.option4).text = q.option4
        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
    }
}
