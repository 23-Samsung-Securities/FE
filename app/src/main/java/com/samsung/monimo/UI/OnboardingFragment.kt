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
