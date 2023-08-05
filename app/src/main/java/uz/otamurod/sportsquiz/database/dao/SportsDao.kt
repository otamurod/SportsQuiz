package uz.otamurod.sportsquiz.database.dao

import androidx.room.Dao
import androidx.room.Query
import uz.otamurod.sportsquiz.database.entity.Sports

@Dao
interface SportsDao {
    @Query("SELECT * FROM Sports")
    fun getAllSports(): List<Sports>

    @Query("SELECT * FROM Sports WHERE id=:id")
    fun getSportById(id: Int): Sports
}