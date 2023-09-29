package com.example.splitexpenses

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitexpenses.databinding.FragmentStartPageBinding

class StartPageFragment : Fragment() {
    private val viewModel: NameViewModel by activityViewModels()
    private lateinit var binding: FragmentStartPageBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartPageBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = MemberNameAdapter(requireContext(), viewModel)
        binding.addMember.setOnClickListener {
            if(binding.nameEditText.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireActivity(), "No members added", Toast.LENGTH_SHORT).show()
            } else if(viewModel.names.value!!.containsKey(binding.nameEditText.text.toString())) {
                Toast.makeText(requireActivity(), "Member already added", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addMember(binding.nameEditText.text.toString())
                recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                recyclerView.adapter = MemberNameAdapter(requireContext(), viewModel)
                binding.nameEditText.text?.clear()
            }
        }
        binding.calculate.setOnClickListener {
            findNavController().navigate(R.id.action_startPageFragment_to_secondPageFragment)
        }
    }
}