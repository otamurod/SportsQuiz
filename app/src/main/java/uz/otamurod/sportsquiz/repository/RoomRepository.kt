package uz.otamurod.sportsquiz.repository

import uz.otamurod.sportsquiz.database.dao.QuestionsDao
import uz.otamurod.sportsquiz.database.dao.SportsDao
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val sportsDao: SportsDao,
    private val questionsDao: QuestionsDao
) {
    // For SportsDao
    fun getAllSports() = sportsDao.getAllSports()
    fun getSportById(id: Int) = sportsDao.getSportById(id)

    // For QuestionsDao
    fun getAllQuestions() = questionsDao.getAllQuestions()
    fun getQuestionsRelatedToSport(sportID: Int) = questionsDao.getQuestionsRelatedToSport(sportID)
}