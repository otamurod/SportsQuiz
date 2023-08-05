package uz.otamurod.sportsquiz.ui.fact

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import uz.otamurod.sportsquiz.adapter.SportsAdapter
import uz.otamurod.sportsquiz.database.entity.Sports
import uz.otamurod.sportsquiz.databinding.FragmentFactBinding

@AndroidEntryPoint
class FactFragment : Fragment(), OnItemClickListener {
    private val factViewModel: FactViewModel by viewModels()
    private lateinit var binding: FragmentFactBinding
    private lateinit var sportsAdapter: SportsAdapter

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentFactBinding.inflate(LayoutInflater.from(container!!.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerLayout.startShimmer()
        factViewModel.sports.observe(viewLifecycleOwner) {
            it.apply {
                initAdapter(it!!)
            }
        }
    }

    private fun initViewModel() {
        factViewModel.getAllSports()
    }

    private fun initAdapter(sports: List<Sports>) {
        sportsAdapter = SportsAdapter(requireActivity(), sports, this)
        binding.recyclerView.adapter = sportsAdapter
        Handler(Looper.getMainLooper()).postDelayed({
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }, 2_000)
    }

    override fun onItemClicked(sports: Sports) {
        val bundle = bundleOf("id" to sports.id)
        findNavController().navigate(R.id.sportsFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }
}