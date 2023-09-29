package com.example.splitexpenses

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitexpenses.databinding.FragmentSecondPageBinding

class SecondPageFragment : Fragment() {
    private val viewModel: NameViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView1: RecyclerView
    private lateinit var binding: FragmentSecondPageBinding
    private var currentSelected: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondPageBinding.inflate(inflater, container, false)
        recyclerView = binding.checkbox
        recyclerView1 = binding.transactions
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val names = viewModel.names.value!!.keys.toList()
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.name, names)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = CheckBoxAdapter(requireActivity(), viewModel)

        recyclerView1.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView1.adapter = TransactionAdapter(requireActivity(), viewModel)

        binding.addTransaction.setOnClickListener {
            addTransaction()
        }

        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            currentSelected = parent.getItemAtPosition(position).toString()
        }

        binding.viewSummary.setOnClickListener {
            findNavController().navigate(R.id.action_secondPageFragment_to_summaryFragment)
        }
    }

    private fun addTransaction() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        val amountSpent = binding.moneySpentEditText.text.toString()
        if(amountSpent == "" || amountSpent.toDouble() == 0.0) {
            Toast.makeText(requireActivity(), "Enter Money Spent", Toast.LENGTH_SHORT).show()
            return
        }
        if (currentSelected == "" || !viewModel.spent(
                amountSpent.toDouble(),
                currentSelected
            )
        ) {
            Toast.makeText(requireActivity(), "Enter all fields", Toast.LENGTH_SHORT).show()
            return
        }
        binding.moneySpentEditText.text?.clear()
        recyclerView1.adapter = TransactionAdapter(requireActivity(), viewModel)
    }
}