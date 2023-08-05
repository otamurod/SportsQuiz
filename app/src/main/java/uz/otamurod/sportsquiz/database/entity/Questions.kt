package uz.otamurod.sportsquiz.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Questions(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var sportID: Int,

    var question: String,

    var option1: String,

    var option2: String,

    var option3: String,

    var option4: String,

    var answer: String
)
