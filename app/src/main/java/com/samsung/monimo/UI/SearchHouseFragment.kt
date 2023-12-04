package com.samsung.monimo.UI

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samsung.monimo.R
import com.samsung.monimo.databinding.FragmentSearchHouseBinding
import kotlin.concurrent.fixedRateTimer

class SearchHouseFragment : Fragment() {

    lateinit var binding: FragmentSearchHouseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchHouseBinding.inflate(inflater)
        
        initView()

        binding.run {
            buttonMap.setOnClickListener {
                // 목표 설정(내 집 마련 - 지도) 화면으로 전환
                val fragment = SearchMapFragment()

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
                title = "목표 설정"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            var fullText = textViewSearchHouse.text

            val spannableString = SpannableString(fullText)

            // 시작 인덱스와 끝 인덱스 사이의 텍스트에 다른 색상 적용
            val startIndex = 0
            val endIndex = 9
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#0B84FE")), // 색상 설정
                startIndex, // 시작 인덱스
                endIndex, // 끝 인덱스 (exclusive)
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE, // 스타일 적용 범위 설정
            )

            textViewSearchHouse.text = spannableString
        }
    }
}
