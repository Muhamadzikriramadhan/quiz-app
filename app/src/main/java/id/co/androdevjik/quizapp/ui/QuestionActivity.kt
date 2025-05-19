package id.co.androdevjik.quizapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.androdevjik.quizapp.R
import id.co.androdevjik.quizapp.adapter.QuestionAdapter
import id.co.androdevjik.quizapp.database.QuizDbHelper
import id.co.androdevjik.quizapp.model.Question

class QuestionActivity : AppCompatActivity() {

    private lateinit var db: QuizDbHelper
    private var questions = mutableListOf<Question>()
    private var selectedQuestionId: Int? = null
    private lateinit var adapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        db = QuizDbHelper(this)

        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val etOption1 = findViewById<EditText>(R.id.etOption1)
        val etOption2 = findViewById<EditText>(R.id.etOption2)
        val etOption3 = findViewById<EditText>(R.id.etOption3)
        val etOption4 = findViewById<EditText>(R.id.etOption4)
        val etAnswer = findViewById<EditText>(R.id.etAnswer)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val rvQuestions = findViewById<RecyclerView>(R.id.rvQuestions)

        rvQuestions.layoutManager = LinearLayoutManager(this)

        fun refreshList() {
            questions = db.getAllQuestions().toMutableList()
            adapter = QuestionAdapter(questions,
                onItemClick = { selected ->
                    selectedQuestionId = selected.id
                    etQuestion.setText(selected.question)
                    etOption1.setText(selected.option1)
                    etOption2.setText(selected.option2)
                    etOption3.setText(selected.option3)
                    etOption4.setText(selected.option4)
                    etAnswer.setText(selected.answer.toString())

                    btnAdd.visibility = View.GONE
                    btnUpdate.visibility = View.VISIBLE
                },
                onItemLongClick = { toDelete ->
                    db.deleteQuestion(toDelete.id)
                    Toast.makeText(this, "Question deleted", Toast.LENGTH_SHORT).show()
                    refreshList()
                }
            )
            rvQuestions.adapter = adapter
        }

        refreshList()

        btnAdd.setOnClickListener {
            val q = etQuestion.text.toString()
            val o1 = etOption1.text.toString()
            val o2 = etOption2.text.toString()
            val o3 = etOption3.text.toString()
            val o4 = etOption4.text.toString()
            val ans = etAnswer.text.toString().toIntOrNull() ?: 1

            if (q.isNotBlank() && o1.isNotBlank() && o2.isNotBlank()) {
                db.insertQuestion(Question(0, q, o1, o2, o3, o4, ans))
                Toast.makeText(this, "Question added", Toast.LENGTH_SHORT).show()
                etQuestion.text.clear()
                etOption1.text.clear()
                etOption2.text.clear()
                etOption3.text.clear()
                etOption4.text.clear()
                etAnswer.text.clear()
                refreshList()
            }
        }

        btnUpdate.setOnClickListener {
            val id = selectedQuestionId ?: return@setOnClickListener
            val updated = Question(
                id,
                etQuestion.text.toString(),
                etOption1.text.toString(),
                etOption2.text.toString(),
                etOption3.text.toString(),
                etOption4.text.toString(),
                etAnswer.text.toString().toIntOrNull() ?: 1
            )
            db.updateQuestion(updated)
            Toast.makeText(this, "Question updated", Toast.LENGTH_SHORT).show()
            btnAdd.visibility = View.VISIBLE
            btnUpdate.visibility = View.GONE
            etQuestion.text.clear()
            etOption1.text.clear()
            etOption2.text.clear()
            etOption3.text.clear()
            etOption4.text.clear()
            etAnswer.text.clear()
            refreshList()
        }

    }
}
