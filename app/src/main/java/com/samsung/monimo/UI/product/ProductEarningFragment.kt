package com.samsung.monimo.UI.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samsung.monimo.API.model.TopResult
import com.samsung.monimo.MainActivity
import com.samsung.monimo.UI.product.Adapter.ProductAdapter
import com.samsung.monimo.UI.product.viewModel.ProductViewModel
import com.samsung.monimo.databinding.FragmentProductEarningBinding

class ProductEarningFragment : Fragment() {

    lateinit var binding: FragmentProductEarningBinding
    lateinit var viewModel: ProductViewModel
    lateinit var mainActivity: MainActivity

    var earningProductList = mutableListOf<TopResult>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductEarningBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        mainActivity = activity as MainActivity
        viewModel.run {
            productList.observe(mainActivity) {
                earningProductList = it
            }
        }

        binding.run {
            recyclerViewProduct.run {
                adapter = ProductAdapter(earningProductList.toTypedArray())

                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return binding.root
    }
}
