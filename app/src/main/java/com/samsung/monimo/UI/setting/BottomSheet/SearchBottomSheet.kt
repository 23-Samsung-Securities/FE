package com.samsung.monimo.UI.BottomSheet

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.samsung.monimo.API.model.ApartmentListResult
import com.samsung.monimo.MainActivity
import com.samsung.monimo.R
import com.samsung.monimo.UI.setting.Adapter.ApartmentListAdapter
import com.samsung.monimo.UI.setting.SettingPeriodFragment
import com.samsung.monimo.UI.setting.viewModel.ApartmentListViewModel
import com.samsung.monimo.Utils.MyApplication
import com.samsung.monimo.databinding.BottomSheetSearchBinding

class SearchBottomSheet(var search: String) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetSearchBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: ApartmentListViewModel

    var apartmentList = mutableListOf<ApartmentListResult>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        val contentView = View.inflate(context, R.layout.bottom_sheet_search, null)
        bottomSheetDialog.setContentView(contentView)

        val behavior = BottomSheetBehavior.from(contentView.parent as View)

//        // Bottom Sheet의 높이를 고정하고자 하는 값으로 설정
//        behavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_height)
//
        // Bottom Sheet의 상태를 COLLAPSED로 설정하여 높이를 고정시킵니다.
//         behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        // 사용자가 Bottom Sheet를 축소할 수 없도록 설정
        behavior.skipCollapsed = true

        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = BottomSheetSearchBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(requireActivity())[ApartmentListViewModel::class.java]

        viewModel.run {
            apartmentInfoList.observe(mainActivity) {
                apartmentList = it
            }
        }

//        val layoutParams = view?.layoutParams
//        layoutParams?.height = resources.getDimensionPixelSize(R.dimen.bottom_sheet_height) // 원하는 높이 값으로 설정
//        view?.layoutParams = layoutParams

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        binding.editTextSearchBottomSheet.setText(search)

        val listAdapter = ApartmentListAdapter(
            apartmentList.toTypedArray(),
        ) // 어댑터

        binding.recyclerViewLocation.run {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(mainActivity)

            listAdapter.itemClickListener = object : ApartmentListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    // 클릭 시 이벤트 작성
                    MyApplication.selectedApartmentId = apartmentList.get(position).apartmentId.toInt()
                    MyApplication.selectedApartmentName = apartmentList.get(position).apartmentName

                    // 목표 설정(내 집 마련 - 기간 설정) 화면으로 전환
                    val fragment = SettingPeriodFragment()

                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                    dismiss()
                }
            }
        }
    }
}
