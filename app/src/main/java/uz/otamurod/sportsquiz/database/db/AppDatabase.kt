package uz.otamurod.sportsquiz.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.otamurod.sportsquiz.database.dao.QuestionsDao
import uz.otamurod.sportsquiz.database.dao.SportsDao
import uz.otamurod.sportsquiz.database.entity.Questions
import uz.otamurod.sportsquiz.database.entity.Sports

@Database(entities = [Sports::class, Questions::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sportsDao(): SportsDao
    abstract fun questionsDao(): QuestionsDao
}