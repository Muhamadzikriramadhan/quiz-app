package id.co.androdevjik.quizapp.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.androdevjik.quizapp.adapter.UserAdapter
import id.co.androdevjik.quizapp.database.QuizDbHelper

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@UserListActivity)
            setBackgroundColor(Color.LTGRAY)
        }

        setContentView(recyclerView)

        val db = QuizDbHelper(this)
        val users = db.getAllUsers()
        recyclerView.adapter = UserAdapter(users)
    }
}
