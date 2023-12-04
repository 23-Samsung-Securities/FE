package com.samsung.monimo.UI

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samsung.monimo.R
import com.samsung.monimo.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResultBinding.inflate(inflater)

        initView()

        binding.run {
            buttonProduct.setOnClickListener {
                // 상품 추천 - 로딩 화면으로 전환
                val fragment = SearchProductFragment()

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
                title = "결과 확인"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_close)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    for (i in 0 until requireActivity().supportFragmentManager.backStackEntryCount) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
        }
    }
}