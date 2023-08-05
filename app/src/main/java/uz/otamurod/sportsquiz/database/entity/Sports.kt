package uz.otamurod.sportsquiz.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sports(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,

    var fact: String
)
