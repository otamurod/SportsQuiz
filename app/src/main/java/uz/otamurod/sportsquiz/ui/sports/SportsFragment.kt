package uz.otamurod.sportsquiz.ui.sports

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.otamurod.sportsquiz.adapter.QuestionsAdapter
import uz.otamurod.sportsquiz.databinding.FragmentSportsBinding

private const val ARG_ID = "id"

@AndroidEntryPoint
class SportsFragment : Fragment() {
    private var id: Int? = null
    private lateinit var binding: FragmentSportsBinding
    private val sportsViewModel: SportsViewModel by viewModels()
    private lateinit var questionsAdapter: QuestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportsBinding.inflate(
            LayoutInflater.from(container!!.context),
            container,
            false
        )
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerLayout.startShimmer()
        sportsViewModel.sport.observe(viewLifecycleOwner) { sport ->
            sport.apply {
                setSportImage(id)
                binding.name.text = name
                binding.fact.text = fact
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.sportsLayout.visibility = View.VISIBLE
                }, 2_000)
            }
        }

        sportsViewModel.question.observe(viewLifecycleOwner) {
            it.apply {
                questionsAdapter = QuestionsAdapter(it)
                binding.questionsRv.adapter = questionsAdapter
            }
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch(Dispatchers.IO) {
            sportsViewModel.getSportById(id!!)
            sportsViewModel.getQuestionsRelatedToSport(id!!)
        }
    }

    private fun setSportImage(id: Int) {
        val uri = "@drawable/sport_$id" // where myresource (without the extension) is the file
        val imageResource: Int =
            requireActivity().resources.getIdentifier(uri, null, requireActivity().packageName)
        val res: Drawable = requireActivity().resources.getDrawable(imageResource)
        binding.sportImage.setImageDrawable(res)
    }
}