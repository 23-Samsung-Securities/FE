package com.samsung.monimo.UI.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.samsung.monimo.API.model.TopResult
import com.samsung.monimo.MainActivity
import com.samsung.monimo.UI.product.Adapter.CategoryAdapter
import com.samsung.monimo.UI.product.Adapter.ProductAdapter
import com.samsung.monimo.UI.product.viewModel.ProductViewModel
import com.samsung.monimo.databinding.FragmentProductInterestBinding

class ProductInterestFragment : Fragment() {

    lateinit var binding: FragmentProductInterestBinding
    lateinit var viewModel: ProductViewModel
    lateinit var mainActivity: MainActivity

    var interestProductList = mutableListOf<TopResult>()

    var categoryList = listOf<String>(
        "IT",
        "화장품",
        "스포츠",
        "카페"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductInterestBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        mainActivity = activity as MainActivity
        
        viewModel.run {
            productInterestList.observe(mainActivity) {
                interestProductList = it

                binding.run {
                    recyclerViewProduct.run {
                        adapter = ProductAdapter(interestProductList.toTypedArray())

                        layoutManager = LinearLayoutManager(mainActivity)

                        adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
        viewModel.getInterestProductList(requireContext(), categoryList[0])

        binding.run {
            recyclerViewInterestList.run {
                adapter = CategoryAdapter(categoryList.toTypedArray(), requireActivity())

                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
        
        return binding.root
    }
}
