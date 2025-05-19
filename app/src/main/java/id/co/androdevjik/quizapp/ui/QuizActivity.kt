package id.co.androdevjik.quizapp.ui

import android.graphics.Color
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
    private var questionTotal: Int = 0
    private var answerShown = false  // <-- To track if answer is already shown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val quizLength = intent.getIntExtra("quizLength", -1)
        val name = intent.getStringExtra("name")
        this.questionTotal = quizLength

        db = QuizDbHelper(this)
        questions = db.getRandomQuestions(questionTotal)
        startTime = System.currentTimeMillis()

        showQuestion()

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            val selected = radioGroup.checkedRadioButtonId
            val selectedOption = when (selected) {
                R.id.option1 -> 1
                R.id.option2 -> 2
                R.id.option3 -> 3
                R.id.option4 -> 4
                else -> 0
            }

            val correctOption = questions[index].answer

            if (!answerShown) {
                // Show correct answer
                highlightAnswer(correctOption, selectedOption)

                // Disable all options
                for (i in 0 until radioGroup.childCount) {
                    radioGroup.getChildAt(i).isEnabled = false
                }

                // Change button text
                btnNext.text = if (index == questions.size - 1) "Selesai" else "Lanjut"
                answerShown = true
            } else {
                // Increase score if correct
                if (selectedOption == correctOption) score++

                index++
                if (index < questions.size) {
                    showQuestion()
                } else {
                    val endTime = System.currentTimeMillis()
                    val duration = (endTime - startTime) / 1000
                    val timeNow = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                        java.util.Date()
                    )
                    db.insertUser(name!!, score, timeNow, questionTotal, "$duration detik")

                    AlertDialog.Builder(this)
                        .setTitle("Selesai!")
                        .setMessage("Skor kamu: $score / $questionTotal\nWaktu: $duration detik")
                        .setPositiveButton("Kembali") { _, _ -> finish() }
                        .show()
                }
                answerShown = false
                btnNext.text = "Selanjutnya"
            }
        }
    }

    private fun showQuestion() {
        val q = questions[index]
        findViewById<TextView>(R.id.tvQuestion).text = q.question

        val option1 = findViewById<RadioButton>(R.id.option1)
        val option2 = findViewById<RadioButton>(R.id.option2)
        val option3 = findViewById<RadioButton>(R.id.option3)
        val option4 = findViewById<RadioButton>(R.id.option4)

        val options = listOf(option1, option2, option3, option4)

        option1.text = q.option1
        option2.text = q.option2
        option3.text = q.option3
        option4.text = q.option4

        // Reset color and enable
        options.forEach {
            it.setTextColor(Color.BLACK)
            it.isEnabled = true
        }

        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
    }

    private fun highlightAnswer(correct: Int, selected: Int) {
        val options = listOf(
            findViewById<RadioButton>(R.id.option1),
            findViewById<RadioButton>(R.id.option2),
            findViewById<RadioButton>(R.id.option3),
            findViewById<RadioButton>(R.id.option4)
        )

        // Highlight correct answer green
        options[correct - 1].setTextColor(Color.GREEN)

        // Highlight wrong answer red
        if (selected != correct && selected in 1..4) {
            options[selected - 1].setTextColor(Color.RED)
        }
    }
}
