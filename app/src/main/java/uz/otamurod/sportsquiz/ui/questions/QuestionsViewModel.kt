package uz.otamurod.sportsquiz.ui.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.otamurod.sportsquiz.database.entity.Questions
import uz.otamurod.sportsquiz.repository.RoomRepository
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private var _allQuestions = MutableLiveData<List<Questions>>()
    var allQuestions: LiveData<List<Questions>> = _allQuestions

    private var _question = MutableLiveData<List<Questions>>()
    var question: LiveData<List<Questions>> = _question

    fun getAllQuestions() { // observe all questions
        viewModelScope.launch(Dispatchers.IO) {
            _allQuestions.postValue(roomRepository.getAllQuestions())
        }
    }

    fun getQuestionsRelatedToSport(sportID: Int) { // observe questions related to a sport
        viewModelScope.launch(Dispatchers.IO) {
            _question.postValue(roomRepository.getQuestionsRelatedToSport(sportID))
        }
    }
}