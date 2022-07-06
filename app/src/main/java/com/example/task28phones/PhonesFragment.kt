package com.example.task28phones

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task28phones.data.JSON_PHONES
import com.example.task28phones.databinding.FragmentPhonesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val EMPTY_STRING = ""

class PhonesFragment : Fragment() {
    private var _binding: FragmentPhonesBinding? = null
    private val binding get() = _binding!!
    private val phonesAdapter = PhonesAdapter()
    private lateinit var tempList: List<DataPhones>

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
        setFilterListener()
        phonesAdapter.submitList(tempList)
    }

    private fun initRecyclerView() {
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

    private fun phonesParse() {
        val listPhonesType = object : TypeToken<List<DataPhones>>() {}.type
        val phones: List<DataPhones> = Gson().fromJson(getPhonesJson(), listPhonesType)
        tempList = phones
    }

    private fun setFilterListener() {
        binding.textFilter.addTextChangedListener { text ->
            val filter = text.toString()
            val newList: List<DataPhones> = tempList.filter { list ->
                list.phone.contains(filter) || list.name.contains(filter)
            }
            saveFilter(filter)
            phonesAdapter.submitList(newList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPhonesJson() = JSON_PHONES
}