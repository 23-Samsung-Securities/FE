package com.samsung.monimo.UI.BottomSheet

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.samsung.monimo.R
import com.samsung.monimo.UI.SettingPeriodFragment
import com.samsung.monimo.databinding.BottomSheetSearchBinding

class SearchBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetSearchBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        val contentView = View.inflate(context, R.layout.bottom_sheet_search, null)
        bottomSheetDialog.setContentView(contentView)

        val behavior = BottomSheetBehavior.from(contentView.parent as View)

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

        binding.root.setOnClickListener {
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
