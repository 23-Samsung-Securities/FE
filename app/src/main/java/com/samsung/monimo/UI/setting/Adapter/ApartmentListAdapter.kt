package com.samsung.monimo.UI.setting.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samsung.monimo.API.model.ApartmentListResult
import com.samsung.monimo.databinding.RowBottomSheetBinding

class ApartmentListAdapter(
    var list: Array<ApartmentListResult>,
) :
    RecyclerView.Adapter<ApartmentListAdapter.ViewHolder>() {
    private var context: Context? = null

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

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: RowBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.textViewName
        val locationValue = binding.textViewLocationValue

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }
}
