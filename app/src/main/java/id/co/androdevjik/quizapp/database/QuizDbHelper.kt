package id.co.androdevjik.quizapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.co.androdevjik.quizapp.model.Question
import id.co.androdevjik.quizapp.model.QuizParticipant
import id.co.androdevjik.quizapp.model.User

class QuizDbHelper(context: Context) : SQLiteOpenHelper(context, "quiz.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question TEXT,
                option1 TEXT,
                option2 TEXT,
                option3 TEXT,
                option4 TEXT,
                answer INTEGER
            );
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE,
                password TEXT,
                name TEXT,
                role TEXT
            );
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE quiz_participants (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                score INTEGER,
                questionTotal INTEGER,
                submitTime TEXT,
                duration TEXT
            );
        """.trimIndent())

        // Insert sample questions
        db.execSQL("""
            INSERT INTO questions (question, option1, option2, option3, option4, answer) VALUES
            ('Apa ibukota Indonesia?', 'Jakarta', 'Bandung', 'Surabaya', 'Medan', 1),
            ('Apa lambang negara Indonesia?', 'Padi', 'Garuda Pancasila', 'Bintang', 'Merah Putih', 2),
            ('Berapa sila dalam Pancasila?', '3', '4', '5', '6', 3),
            ('Apa semboyan negara Indonesia?', 'Indonesia Hebat', 'Merdeka atau Mati', 'Bhinneka Tunggal Ika', 'Tanah Air Beta', 3),
            ('Siapa pencipta lagu Indonesia Raya?', 'WR Supratman', 'Soekarno', 'Ismail Marzuki', 'H. Mutahar', 1),
            ('Tanggal berapa Indonesia merdeka?', '17 Juli 1945', '17 Agustus 1945', '1 Juni 1945', '1 Agustus 1945', 2),
            ('Berapa provinsi di Indonesia (per 2024)?', '33', '34', '37', '38', 4),
            ('Ibu kota provinsi Jawa Barat adalah?', 'Bandung', 'Jakarta', 'Surabaya', 'Semarang', 1),
            ('Siapa yang mengetuai BPUPKI?', 'Moh. Hatta', 'Soekarno', 'Radjiman Wedyodiningrat', 'Sutomo', 3),
            ('Siapa presiden pertama RI?', 'Soekarno', 'Soeharto', 'Jokowi', 'Habibie', 1),
            ('2 + 2 = ?', '3', '4', '5', '6', 2),
            ('5 + 7 = ?', '10', '11', '12', '13', 3),
            ('12 - 5 = ?', '5', '6', '7', '8', 3),
            ('6 x 3 = ?', '18', '12', '15', '9', 1),
            ('20 ÷ 4 = ?', '4', '5', '6', '3', 2),
            ('Akar kuadrat dari 49 adalah?', '5', '6', '7', '8', 3),
            ('Hasil dari 3² adalah?', '6', '9', '12', '15', 2),
            ('Jika 8 + x = 13, maka x = ?', '4', '5', '6', '7', 2),
            ('10% dari 200 adalah?', '10', '20', '25', '30', 2),
            ('3 x 4 + 2 = ?', '12', '14', '16', '18', 2),
            ('7 + 7 = ?', '13', '16', '14', '15', 3),
            ('Kapan Sumpah Pemuda diucapkan?', '28 Oktober 1928', '17 Agustus 1945', '1 Juni 1945', '20 Mei 1908', 1),
            ('6 + 14 = ?', '20', '21', '22', '19', 1),
            ('9 + 9 = ?', '19', '18', '17', '20', 2),
            ('16 + 17 = ?', '35', '34', '33', '32', 3),
            ('14 + 18 = ?', '32', '33', '34', '31', 1),
            ('Apa warna bendera Indonesia?', 'Merah dan Putih', 'Merah dan Kuning', 'Putih dan Biru', 'Merah dan Biru', 1),
            ('2 + 9 = ?', '13', '12', '10', '11', 4),
            ('16 + 3 = ?', '21', '18', '19', '20', 3),
            ('Apa arti Bhinneka Tunggal Ika?', 'Berbeda-beda tetapi tetap satu', 'Bersatu kita teguh', 'Satu nusa satu bangsa', 'Merdeka atau mati', 1),
            ('2 + 20 = ?', '21', '23', '22', '24', 3),
            ('6 + 1 = ?', '7', '8', '6', '9', 1),
            ('15 + 11 = ?', '26', '25', '27', '28', 1),
            ('Apa dasar negara Indonesia?', 'UUD 1945', 'Pancasila', 'Bhinneka Tunggal Ika', 'Proklamasi', 2),
            ('10 + 14 = ?', '25', '23', '24', '26', 3),
            ('18 + 1 = ?', '19', '18', '20', '21', 1),
            ('4 + 16 = ?', '20', '21', '22', '19', 1),
            ('13 + 4 = ?', '16', '18', '17', '19', 3),
            ('Siapa wakil presiden pertama Indonesia?', 'Moh. Hatta', 'Soeharto', 'BJ Habibie', 'Megawati', 1),
            ('19 + 5 = ?', '26', '23', '24', '25', 3),
            ('10 + 8 = ?', '19', '18', '17', '20', 2),
            ('17 + 9 = ?', '26', '28', '27', '25', 1),
            ('17 + 20 = ?', '37', '38', '36', '39', 1),
            ('15 + 16 = ?', '30', '33', '31', '32', 3),
            ('3 + 2 = ?', '5', '7', '6', '4', 1),
            ('16 + 10 = ?', '27', '25', '28', '26', 4),
            ('7 + 2 = ?', '11', '8', '10', '9', 4),
            ('15 + 8 = ?', '25', '23', '22', '24', 2),
            ('4 + 5 = ?', '8', '9', '11', '10', 2),
            ('9 + 13 = ?', '21', '22', '23', '24', 2),
            ('14 + 5 = ?', '21', '19', '18', '20', 2),
            ('4 + 14 = ?', '20', '18', '19', '17', 2),
            ('16 + 2 = ?', '19', '18', '20', '17', 2);
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS questions")
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS quiz_participants")
        onCreate(db)
    }

    fun getRandomQuestions(limit: Int = 5): List<Question> {
        val list = mutableListOf<Question>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT * FROM questions ORDER BY RANDOM() LIMIT $limit", null)
        while (cursor.moveToNext()) {
            list.add(
                Question(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6)
                )
            )
        }
        cursor.close()
        return list
    }

    fun loginUser(username: String, password: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE username = ? AND password = ? ORDER BY username ASC",
            arrayOf(username, password)
        )
        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getIntSafe("id"),
                username = cursor.getStringSafe("username"),
                password = cursor.getStringSafe("password"),
                name = cursor.getStringSafe("name"),
                role = cursor.getStringSafe("role")
            )

            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun registerUser(username: String, password: String, name: String, role: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
            put("name", name)
            put("role", role)
        }
        return try {
            db.insertOrThrow("users", null, values)
            true
        } catch (e: SQLiteConstraintException) {
            false // username already exists
        }
    }

    fun insertUser(name: String, score: Int, submitTime: String, questionTotal: Int, duration: String) {

        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("score", score)
            put("questionTotal", questionTotal)
            put("submitTime", submitTime)
            put("duration", duration)
        }
        db.insert("quiz_participants", null, values)
    }

    fun getAllUsers(): List<QuizParticipant> {
        val list = mutableListOf<QuizParticipant>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM quiz_participants", null)
        while (cursor.moveToNext()) {
            list.add(QuizParticipant(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5)
            ))
        }
        cursor.close()
        return list
    }

    fun Cursor.getStringSafe(column: String): String? {
        return try {
            val index = getColumnIndexOrThrow(column)
            if (isNull(index)) null else getString(index)
        } catch (e: Exception) {
            null
        }
    }

    fun Cursor.getIntSafe(column: String): Int {
        return try {
            val index = getColumnIndexOrThrow(column)
            if (isNull(index)) 0 else getInt(index)
        } catch (e: Exception) {
            0
        }
    }

    fun insertQuestion(q: Question): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("question", q.question)
            put("option1", q.option1)
            put("option2", q.option2)
            put("option3", q.option3)
            put("option4", q.option4)
            put("answer", q.answer)
        }
        return db.insert("questions", null, values)
    }

    fun getAllQuestions(): List<Question> {
        val questions = mutableListOf<Question>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM questions", null)
        if (cursor.moveToFirst()) {
            do {
                val question = Question(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    question = cursor.getString(cursor.getColumnIndexOrThrow("question")),
                    option1 = cursor.getString(cursor.getColumnIndexOrThrow("option1")),
                    option2 = cursor.getString(cursor.getColumnIndexOrThrow("option2")),
                    option3 = cursor.getString(cursor.getColumnIndexOrThrow("option3")),
                    option4 = cursor.getString(cursor.getColumnIndexOrThrow("option4")),
                    answer = cursor.getInt(cursor.getColumnIndexOrThrow("answer"))
                )
                questions.add(question)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return questions
    }

    fun updateQuestion(q: Question): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("question", q.question)
            put("option1", q.option1)
            put("option2", q.option2)
            put("option3", q.option3)
            put("option4", q.option4)
            put("answer", q.answer)
        }
        return db.update("questions", values, "id=?", arrayOf(q.id.toString()))
    }

    fun deleteQuestion(id: Int): Int {
        val db = writableDatabase
        return db.delete("questions", "id=?", arrayOf(id.toString()))
    }

}
