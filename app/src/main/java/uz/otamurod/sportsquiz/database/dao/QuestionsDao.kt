package uz.otamurod.sportsquiz.database.dao

import androidx.room.*
import uz.otamurod.sportsquiz.database.entity.Questions

@Dao
interface QuestionsDao {
    @Query("SELECT * FROM Questions")
    fun getAllQuestions(): List<Questions>

    @Query("SELECT * FROM Questions WHERE sportID=:sportID")
    fun getQuestionsRelatedToSport(sportID: Int): List<Questions>
}