package com.samsung.monimo.UI.setting.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.samsung.monimo.API.model.ApartmentListResult
import com.samsung.monimo.R
import com.samsung.monimo.UI.setting.SettingPeriodFragment
import com.samsung.monimo.Utils.MyApplication
import com.samsung.monimo.databinding.RowBottomSheetBinding

class ApartmentListAdapter(
    var list: Array<ApartmentListResult>,
    var manager: FragmentManager,
) :
    RecyclerView.Adapter<ApartmentListAdapter.ViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list.get(position).apartmentName
        holder.locationValue.text = list.get(position).address
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: RowBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.textViewName
        val locationValue = binding.textViewLocationValue

        init {
            binding.root.setOnClickListener {

                MyApplication.selectedApartmentId = list.get(adapterPosition).apartmentId.toInt()
                MyApplication.selectedApartmentName = list.get(adapterPosition).apartmentName

                // 목표 설정(내 집 마련 - 기간 설정) 화면으로 전환
                val fragment = SettingPeriodFragment()

                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

                true
            }
        }
    }
}
