package id.co.androdevjik.quizapp.model

data class QuizParticipant(
    val id: Int,
    val name: String?,
    val score: Int,
    val questionTotal: Int,
    val submitTime: String?,
    val duration: String?
)

