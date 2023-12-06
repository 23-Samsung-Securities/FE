package com.samsung.monimo.UI.result

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.samsung.monimo.MainActivity
import com.samsung.monimo.R
import com.samsung.monimo.UI.product.SearchProductFragment
import com.samsung.monimo.UI.product.viewModel.ProductViewModel
import com.samsung.monimo.UI.result.viewModel.CalculateRoiViewModel
import com.samsung.monimo.Utils.MyApplication
import com.samsung.monimo.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var binding: FragmentResultBinding
    lateinit var viewModel: CalculateRoiViewModel
    lateinit var productViewModel: ProductViewModel
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(requireActivity())[CalculateRoiViewModel::class.java]
        productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        viewModel.run {
            apartmentName.observe(mainActivity) {
                binding.textViewSettingBuildingValue.text = it.toString()
            }
            period.observe(mainActivity) {
                binding.textViewSettingPeriodValue.text = it.toString()
            }
            roi.observe(mainActivity) {
                binding.run {
                    MyApplication.roi = it.toString()
                    textViewRequiredEarningValue.text = it.toString()
                    textViewRoi.text = it.toString() + "% 수익률"
                }
            }
        }

        initView()

        binding.run {
            buttonProduct.setOnClickListener {

                productViewModel.getEarningProductList(requireContext(), "top100")
                // 상품 추천 - 로딩 화면으로 전환
                val fragment = SearchProductFragment()

                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            imageViewShareLink.setOnClickListener {
                Toast.makeText(requireContext(), "복사가 완료되었습니다.", Toast.LENGTH_SHORT).show()
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
