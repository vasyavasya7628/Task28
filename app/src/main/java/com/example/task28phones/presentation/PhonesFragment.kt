package com.example.task28phones.presentation

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task28phones.R
import com.example.task28phones.data.DataPhones
import com.example.task28phones.data.JSON_PHONES
import com.example.task28phones.data.PhonesStore
import com.example.task28phones.databinding.FragmentPhonesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val EMPTY_STRING = ""

class PhonesFragment : Fragment() {
    private var _binding: FragmentPhonesBinding? = null
    private val binding get() = _binding!!
    private val phonesAdapter = PhonesAdapter { makeCall(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        phonesParse()
        listenFilter()
        phonesAdapter.submitList(PhonesStore.list)
    }

    private fun initRecyclerView() {
        val didider = DividerItemDecoration(activity, DividerItemDecoration.HORIZONTAL)
        binding.recyclerViewPhones.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewPhones.adapter = phonesAdapter
    }

    private fun saveFilter(filter: String) {
        val sharedPrefWrite =
            binding.root.context.getSharedPreferences(EMPTY_STRING, MODE_PRIVATE) ?: return
        with(sharedPrefWrite.edit()) {
            putString(getString(R.string.string_preference_file_key), filter)
            apply()
        }
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
        PhonesStore.list = phones
    }

    private fun listenFilter() {
        binding.textFilter.addTextChangedListener { text ->
            val filter = text.toString()
            val newList: List<DataPhones> = ((PhonesStore.list?.filter { list ->
                list.phone.contains(filter) || list.name.contains(filter)
            } ?: saveFilter(filter)) as List<DataPhones>)
            phonesAdapter.submitList(newList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPhonesJson() = JSON_PHONES
}