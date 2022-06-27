package com.example.task28phones

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task28phones.data.JSONPHONES
import com.example.task28phones.databinding.FragmentPhonesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhonesFragment : Fragment() {
    private var _binding: FragmentPhonesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var textFilter: EditText
    private val phonesAdapter by lazy {
        PhonesListAdapter()
    }
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
        recyclerView = binding.recyclerViewPhones
        textFilter = binding.textFilter
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = phonesAdapter
    }

    private fun saveFilter(filter: String) {
        val sharedPrefWrite = this.activity?.getPreferences(Context.MODE_PRIVATE) ?: return
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
        textFilter.addTextChangedListener {
            val filter = it.toString()
            val newList: List<DataPhones> = tempList.filter { i ->
                i.phone.contains(filter) || i.name.contains(filter)
            }
            saveFilter(filter)
            phonesAdapter.submitList(newList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPhonesJson() = JSONPHONES
}