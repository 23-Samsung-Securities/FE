package com.samsung.monimo.UI.setting.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.samsung.monimo.API.model.ApartmentListResult
import com.samsung.monimo.databinding.RowBottomSheetBinding

class ApartmentListAdapter(var list: Array<ApartmentListResult>, var manager: FragmentManager) :
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
                true
            }
        }
    }
}
