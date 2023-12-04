package com.samsung.monimo.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samsung.monimo.R
import com.samsung.monimo.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater)

        initView()

        binding.run {
            buttonNext.setOnClickListener {
                // 목표 설정(내 집 마련 - 검색) 화면으로 전환
                val fragment = SearchHouseFragment()

                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "내 집 마련"
            }
        }
    }
}
