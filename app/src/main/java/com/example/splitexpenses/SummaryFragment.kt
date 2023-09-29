package com.example.splitexpenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitexpenses.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
    private val viewModel: NameViewModel by activityViewModels()
    private lateinit var binding: FragmentSummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.calculate()
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding.transactionSummary.layoutManager = LinearLayoutManager(requireActivity())
        binding.transactionSummary.adapter = SummaryAdapter(requireActivity(), viewModel)
        return binding.root
    }

}