package uz.otamurod.sportsquiz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import uz.otamurod.sportsquiz.adapter.ViewPagerAdapter
import uz.otamurod.sportsquiz.databinding.FragmentHomeBinding
import uz.otamurod.sportsquiz.ui.fact.FactFragment
import uz.otamurod.sportsquiz.ui.quiz.QuizFragment

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentHomeBinding.inflate(LayoutInflater.from(container!!.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFragment(FactFragment(), "Facts")
        adapter.addFragment(QuizFragment(), "Quiz")

        // setting adapter to view pager.
        viewPager.adapter = adapter
    }
}