package com.samsung.monimo.UI.product.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samsung.monimo.API.model.TopResult
import com.samsung.monimo.databinding.RowProductBinding
import java.text.DecimalFormat

class ProductAdapter(
    var list: Array<TopResult>,
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list.get(position).name
        val dec = DecimalFormat("#,###")
        var formattedPrice = dec.format(list.get(position).price.toInt())
        holder.price.text = formattedPrice
        holder.earningRate.text = "(+ ${list.get(position).roi}% )"
        holder.ranking.text = (position + 1).toString()
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: RowProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.textViewProductName
        val price = binding.textViewPrice
        val earningRate = binding.textViewEarningRate
        val ranking = binding.textViewRanking
    }
}
