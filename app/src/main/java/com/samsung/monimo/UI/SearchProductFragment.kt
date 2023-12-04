package com.samsung.monimo.UI

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samsung.monimo.R
import com.samsung.monimo.databinding.FragmentSearchProductBinding

class SearchProductFragment : Fragment() {

    lateinit var binding: FragmentSearchProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchProductBinding.inflate(inflater)


        return binding.root
    }
}
