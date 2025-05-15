package id.co.androdevjik.quizapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.co.androdevjik.quizapp.R
import id.co.androdevjik.quizapp.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: TextView = view.findViewById(R.id.iconUser)
        val name: TextView = view.findViewById(R.id.nameText)
        val info: TextView = view.findViewById(R.id.infoText)
        val datetime: TextView = view.findViewById(R.id.datetime)
        val time: TextView = view.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy, h:mm a", Locale("id"))
        val date: Date = inputFormat.parse(user.submitTime)!!
        val formatted = outputFormat.format(date)

        holder.datetime.text = formatted
        holder.time.text = user.duration
        holder.name.text = user.name
        holder.icon.text = user.name.first().uppercaseChar().toString()
        holder.info.text = "Skor: ${user.score} / ${user.questionTotal}"
    }

    override fun getItemCount(): Int = users.size
}
