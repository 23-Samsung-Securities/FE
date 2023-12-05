package com.samsung.monimo.UI.product

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.samsung.monimo.R
import com.samsung.monimo.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    lateinit var binding: FragmentProductBinding

    val fragmentList = mutableListOf<Fragment>()

    // 탭에 표시할 이름
    val tabName = arrayOf(
        "수익률",
        "관심 분야",
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductBinding.inflate(inflater)

        initView()

        fragmentList.clear()
        fragmentList.add(ProductEarningFragment())
        fragmentList.add(ProductInterestFragment())

        binding.run {
            pagerTab.setUserInputEnabled(false)
            pagerTab.adapter = TabAdapterClass(requireActivity())

            // 탭 구성
            val tabLayoutMediator = TabLayoutMediator(tab, pagerTab) { tab: TabLayout.Tab, i: Int ->
                tab.text = tabName[i]
            }
            tabLayoutMediator.attach()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentList.clear()
        fragmentList.add(ProductEarningFragment())
        fragmentList.add(ProductInterestFragment())
        binding.pagerTab.requestLayout()
    }

    // adapter 클래스
    inner class TabAdapterClass(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "상품 추천"

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