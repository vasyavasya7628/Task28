package com.example.task28phones.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task28phones.data.DataPhones
import com.example.task28phones.data.JSON_PHONES
import com.example.task28phones.databinding.FragmentPhonesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhonesFragment : Fragment() {
    private var _binding: FragmentPhonesBinding? = null
    private val binding get() = _binding!!
    private val phonesAdapter = PhonesAdapter { makeCall(it) }
    private lateinit var phonesList: List<DataPhones>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPhonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        phonesParse()
        saveFilterState()
        addDataToAdapter()
    }

    private fun addDataToAdapter() {
        phonesAdapter.submitList(phonesList)
    }

    private fun initRecyclerView() {
        binding.recyclerViewPhones.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewPhones.adapter = phonesAdapter
    }

    private fun makeCall(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            Toast.makeText(
                requireContext(),
                e.toString(), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun phonesParse() {
        val listPhonesType = object : TypeToken<List<DataPhones>>() {}.type
        val phones: List<DataPhones> = Gson().fromJson(getPhonesJson(), listPhonesType)
        phonesList = phones
    }

    private fun saveFilterState() {
        binding.textFilter.addTextChangedListener { searchText ->
            val filter = phonesList.filter { list ->
                list.phone.contains(searchText.toString()) || list.name.contains(searchText.toString())
            }
            phonesAdapter.submitList(filter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPhonesJson() = JSON_PHONES
}