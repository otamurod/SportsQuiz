package uz.otamurod.sportsquiz.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.otamurod.sportsquiz.R
import uz.otamurod.sportsquiz.adapter.OnItemClickListener
import uz.otamurod.sportsquiz.adapter.QuizAdapter
import uz.otamurod.sportsquiz.database.entity.Sports
import uz.otamurod.sportsquiz.databinding.FragmentQuizBinding

@AndroidEntryPoint
class QuizFragment : Fragment(), OnItemClickListener {
    private val quizVewModel: QuizVewModel by viewModels()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var quizAdapter: QuizAdapter

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentQuizBinding.inflate(LayoutInflater.from(container!!.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizVewModel.sports.observe(viewLifecycleOwner) {
            it.apply {
                initAdapter(it!!)
            }
        }

        binding.shuffledBtn.setOnClickListener {
            val bundle = bundleOf("sportId" to -1)
            findNavController().navigate(R.id.questionsFragment, bundle)        }
    }

    private fun initViewModel() {
        quizVewModel.getSportsCategory()
    }

    private fun initAdapter(sports: List<Sports>) {
        quizAdapter = QuizAdapter(requireActivity(), sports, this)
        binding.recyclerView.adapter = quizAdapter
    }

    override fun onItemClicked(sports: Sports) {
        val bundle = bundleOf("sportId" to sports.id)
        findNavController().navigate(R.id.questionsFragment, bundle)
    }
}