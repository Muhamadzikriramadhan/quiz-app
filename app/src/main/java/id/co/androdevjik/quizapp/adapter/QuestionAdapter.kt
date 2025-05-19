package id.co.androdevjik.quizapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.co.androdevjik.quizapp.R
import id.co.androdevjik.quizapp.model.Question

class QuestionAdapter(
    private val questions: List<Question>,
    private val onItemClick: (Question) -> Unit,
    private val onItemLongClick: (Question) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.tvQuestion.text = question.question

        holder.itemView.setOnClickListener {
            onItemClick(question)
        }

        holder.ivDelete.setOnClickListener {
            onItemLongClick(question)
        }
    }

    override fun getItemCount(): Int = questions.size
}

