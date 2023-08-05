package uz.otamurod.sportsquiz.ui.sports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.otamurod.sportsquiz.database.entity.Questions
import uz.otamurod.sportsquiz.database.entity.Sports
import uz.otamurod.sportsquiz.repository.RoomRepository
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private var _sport = MutableLiveData<Sports>()
    var sport: LiveData<Sports> = _sport

    private var _question = MutableLiveData<List<Questions>>()
    var question: LiveData<List<Questions>> = _question

    fun getSportById(id: Int) { // observe sport
        viewModelScope.launch(Dispatchers.IO) {
            _sport.postValue(roomRepository.getSportById(id))
        }
    }

    fun getQuestionsRelatedToSport(id: Int) { // observe questions related to a sport
        viewModelScope.launch(Dispatchers.IO) {
            _question.postValue(roomRepository.getQuestionsRelatedToSport(id))
        }
    }
}