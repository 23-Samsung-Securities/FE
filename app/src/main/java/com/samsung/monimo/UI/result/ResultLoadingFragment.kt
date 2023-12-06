package com.samsung.monimo.UI.result

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samsung.monimo.R
import com.samsung.monimo.UI.product.ProductFragment
import com.samsung.monimo.databinding.FragmentResultLoadingBinding

class ResultLoadingFragment : Fragment() {

    lateinit var binding: FragmentResultLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentResultLoadingBinding.inflate(inflater)

        return binding.root
    }
}
