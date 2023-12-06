package com.samsung.monimo.UI.result

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samsung.monimo.R
import com.samsung.monimo.UI.product.ProductFragment
import com.samsung.monimo.databinding.FragmentResultLoadingBinding

class ResultLoadingFragment : Fragment() {

    lateinit var binding: FragmentResultLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentResultLoadingBinding.inflate(inflater)

        Handler(Looper.getMainLooper()).postDelayed({
            // 상품 추천 화면으로 전환
            val fragment = ResultFragment()

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }, 2500)

        return binding.root
    }
}
