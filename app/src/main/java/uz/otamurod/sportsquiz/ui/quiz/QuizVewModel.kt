package uz.otamurod.sportsquiz.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.otamurod.sportsquiz.database.entity.Sports
import uz.otamurod.sportsquiz.repository.RoomRepository
import javax.inject.Inject

@HiltViewModel
class QuizVewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private var _sports = MutableLiveData<List<Sports>>()
    var sports: LiveData<List<Sports>> = _sports

    fun getSportsCategory() { // observe sports
        viewModelScope.launch(Dispatchers.IO) {
            _sports.postValue(roomRepository.getAllSports())
        }
    }
}