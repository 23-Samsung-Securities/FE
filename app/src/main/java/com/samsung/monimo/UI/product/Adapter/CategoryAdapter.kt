package com.samsung.monimo.UI.product.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.samsung.monimo.R
import com.samsung.monimo.UI.product.viewModel.ProductViewModel
import com.samsung.monimo.databinding.RowInterestListBinding

class CategoryAdapter(
    var list: Array<String>,
    var activity: ViewModelStoreOwner,
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    lateinit var viewModel: ProductViewModel

    private var selectedItemPosition = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowInterestListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        viewModel = ViewModelProvider(activity)[ProductViewModel::class.java]

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.category.text = "#" + list.get(position)
        holder.category.run {
            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0068FF"))
            setTextColor(resources.getColor(R.color.white))
        }
        if (position != selectedItemPosition) {
            // 다른 아이템들의 색상 변경을 위해 원하는 작업을 수행하세요.
            holder.category.run {
                backgroundTintList = ColorStateList.valueOf(Color.parseColor("#EFF0F1"))
                setTextColor(resources.getColor(R.color.dark_gray))
            }
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: RowInterestListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val category = binding.buttonCategory

        init {
            binding.buttonCategory.setOnClickListener {
                val previousSelected = selectedItemPosition
                selectedItemPosition = adapterPosition
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedItemPosition)
                viewModel.getInterestProductList(context!!, list[adapterPosition])
                binding.buttonCategory.run {
                    backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0068FF"))
                    setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }
}
