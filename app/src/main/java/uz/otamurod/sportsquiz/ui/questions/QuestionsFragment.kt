package uz.otamurod.sportsquiz.ui.questions

import android.media.MediaPlayer
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.otamurod.sportsquiz.R
import uz.otamurod.sportsquiz.database.entity.Questions
import uz.otamurod.sportsquiz.databinding.BottomSheetDialogBinding
import uz.otamurod.sportsquiz.databinding.FragmentQuestionsBinding

private const val ARG_SPORT_ID = "sportId"

@AndroidEntryPoint
class QuestionsFragment : Fragment(), View.OnClickListener {
    private lateinit var question: Questions
    private var sportId: Int? = null
    private lateinit var binding: FragmentQuestionsBinding
    private val questionsViewModel: QuestionsViewModel by viewModels()
    lateinit var dialogBinding: BottomSheetDialogBinding
    private lateinit var questions: List<Questions>
    private var questionNumber = 0
    private var correct = 0
    private var wrong = 0
    private var unanswered = 0
    private var timer: CountDownTimer? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sportId = it.getInt(ARG_SPORT_ID)
            initViewModel()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsBinding.inflate(
            LayoutInflater.from(container!!.context), container, false
        )
        dialogBinding = BottomSheetDialogBinding.inflate(
            LayoutInflater.from(container.context), container, false
        )

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionsViewModel.question.observe(viewLifecycleOwner) { listOfQuestions ->
            listOfQuestions.apply {
                questions = listOfQuestions
                setQuestions()
            }
        }

        questionsViewModel.allQuestions.observe(viewLifecycleOwner) { listOfQuestions ->
            listOfQuestions.apply {
                questions = listOfQuestions.shuffled().subList(0, 10)
                setQuestions()
            }
        }

        binding.apply {
            option1.setOnClickListener(this@QuestionsFragment)
            option2.setOnClickListener(this@QuestionsFragment)
            option3.setOnClickListener(this@QuestionsFragment)
            option4.setOnClickListener(this@QuestionsFragment)
        }
    }

    private fun loadSound() {
        mediaPlayer = MediaPlayer.create(
            requireContext(), R.raw.countdown_bleeps
        )
    }

    private fun showResult() {
        val dialog = BottomSheetDialog(requireContext())
        dialogBinding.correctAnswers.text = String.format("Correct Answers: $correct")
        dialogBinding.wrongAswers.text = String.format("Wrong Answers: $wrong")
        dialogBinding.unanswered.text = String.format("Unanswered Questions: $unanswered")

        dialogBinding.exit.setOnClickListener {
            requireActivity().onBackPressed()
            dialog.dismiss()
        }
        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setQuestions() {
        resetButtons()
        if (questionNumber == questions.size) {
            timer?.cancel()
            timer = null

            showResult()
        } else if (!questions.isNullOrEmpty()) {
            startTimer() // Start the timer for the next question
            question = questions[questionNumber]
            binding.apply {
                questionTv.text = String.format("Q. %s", question.question)
                option1.text = question.option1
                option2.text = question.option2
                option3.text = question.option3
                option4.text = question.option4
                updateScore()
            }
            questionNumber++
        }
    }

    private fun startTimer() {
        loadSound() // Load the sound file

        timer = object : CountDownTimer(31_000, 1_000) { // 30 seconds timer + 1 s for delay
            override fun onTick(millisUntilFinished: Long) {
                // Update the timer view with the remaining time (in seconds)
                binding.timerTextView.text = String.format("%02d", millisUntilFinished / 1_000)

                // Play the sound if time left is less than 5 seconds
                if (millisUntilFinished <= 6_000 && mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                    mediaPlayer?.start()
                }
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFinish() {
                // Time's up, move to the next question
                binding.timeIsUp.isVisible = true // Indicate time's up
                mediaPlayer?.release()
                timer?.cancel()
                unanswered++
                updateScore()
                Handler(Looper.getMainLooper()).postDelayed({
                    setQuestions()
                }, 1000)
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun resetButtons() {
        binding.timeIsUp.isVisible = false
        binding.option1.isClickable = true
        binding.option2.isClickable = true
        binding.option3.isClickable = true
        binding.option4.isClickable = true

        binding.option1.background.setTintList(null)
        binding.option2.background.setTintList(null)
        binding.option3.background.setTintList(null)
        binding.option4.background.setTintList(null)
    }

    private fun updateScore() {
        if (questionNumber < questions.size) {
            binding.questionNumber.text = String.format("%02d", questionNumber + 1)
        }
        binding.correct.text = correct.toString()
        binding.wrong.text = wrong.toString()
    }

    private fun initViewModel() {
        if (sportId != null && sportId != -1) {
            questionsViewModel.getQuestionsRelatedToSport(sportId!!)
        } else if (sportId == -1) {
            questionsViewModel.getAllQuestions()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(view: View?) {
        if (!questions.isNullOrEmpty()) {
            binding.option1.isClickable = false
            binding.option2.isClickable = false
            binding.option3.isClickable = false
            binding.option4.isClickable = false

            when (view?.id) {
                R.id.option1 -> {
                    goToNextQuestion(view, binding.option1.text.toString())
                }
                R.id.option2 -> {
                    goToNextQuestion(view, binding.option2.text.toString())
                }
                R.id.option3 -> {
                    goToNextQuestion(view, binding.option3.text.toString())
                }
                R.id.option4 -> {
                    goToNextQuestion(view, binding.option4.text.toString())
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun goToNextQuestion(view: View?, givenAnswer: String) {
        // Cancel the timer since the user answered the question manually
        timer?.cancel()

        val isCorrect = givenAnswer == question.answer

        if (isCorrect) {
            if (correct < questions.size) {
                changeBackgroundColor(view, R.color.light_green_700)
                correct++
                updateScore()
            }
        } else {
            changeBackgroundColor(view, R.color.red_700)
            wrong++
            updateScore()
        }

        // Delay setting the next question for 1 second (adjust the delay time as needed)
        Handler(Looper.getMainLooper()).postDelayed({
            setQuestions()
        }, 1000)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun changeBackgroundColor(view: View?, color: Int) {
        when (view?.id) {
            R.id.option1 -> {
                binding.option1.background.setTint(
                    ContextCompat.getColor(
                        requireContext(), color
                    )
                )
            }
            R.id.option2 -> {
                binding.option2.background.setTint(
                    ContextCompat.getColor(
                        requireContext(), color
                    )
                )
            }
            R.id.option3 -> {
                binding.option3.background.setTint(
                    ContextCompat.getColor(
                        requireContext(), color
                    )
                )
            }
            R.id.option4 -> {
                binding.option4.background.setTint(
                    ContextCompat.getColor(
                        requireContext(), color
                    )
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        mediaPlayer?.release()
    }
}