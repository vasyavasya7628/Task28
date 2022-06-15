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
import com.example.task28phones.databinding.FragmentPhonesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

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
        initVar()
        phonesParse()
        setFilterListener()
        Timber.tag("PhonesList").d(tempList.toString())
        phonesAdapter.submitList(tempList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initVar() {
        recyclerView = binding.recyclerViewPhones
        textFilter = binding.textFilter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = phonesAdapter
    }

    private fun saveFilter(filter: String) {

        val sharedPrefWrite = this.activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPrefWrite.edit()) {
            putString(getString(R.string.string_preference_file_key), filter)
            apply()
        }
        val sharedPrefRead = this.activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = getString(R.string.defaultValue)
        val sharedReadValue =
            sharedPrefRead.getString(getString(R.string.string_preference_file_key), defaultValue)
        Timber.d("TIMBER LOG FOR READ VALUE:$sharedReadValue")
    }

    private fun phonesParse() {
        val listPhonesType = object : TypeToken<List<DataPhones>>() {}.type//анонимный класс
        val phones: List<DataPhones> = Gson().fromJson(getPhonesJson(), listPhonesType)
        tempList = phones
        Timber.d("var phones: List<PhonesGenerator> $phones")
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

    private fun getPhonesJson() = JSONPHONES
}

const val JSONPHONES = """
    [
  {
    "name": "(Приёмная)",
    "phone": "+375 (2239) 7-17-80",
    "type": ""
  },
  {
    "name": "(Бухгалтерия)",
    "phone": "+375 (2239) 7-17-64",
    "type": ""
  },
  {
    "name": "(Бухгалтерия)",
    "phone": "+375 (2239) 7-18-08",
    "type": ""
  },
  {
    "name": "(Юридическое бюро)",
    "phone": "+375 (2239) 7-17-63",
    "type": ""
  },
  {
    "name": "(Отдел правовой и кадровой работы)",
    "phone": "+375 (2239) 7-17-93",
    "type": ""
  },
  {
    "name": "(Отдел материально-технического снабжения)",
    "phone": "+375 (2239) 7-18-12",
    "type": ""
  },
  {
    "name": "",
    "phone": "+375 44 712 36 26",
    "type": "Сектор сбыта бумаги"
  },
  {
    "name": "(Реализация на внутренний рынок)",
    "phone": "+375 (2239) 7-17-79",
    "type": "Сектор сбыта бумаги"
  },
  {
    "name": "(Реализация на внешний рынок)",
    "phone": "+375 (2239) 4-11-77",
    "type": "Сектор сбыта бумаги"
  },
  {
    "name": "(Реализация на внутренний рынок)",
    "phone": "+375 (2239) 7-18-25",
    "type": "Сектор сбыта бумаги"
  },
  {
    "name": "",
    "phone": "+375 44 580 09 70",
    "type": "Сектор сбыта продукции деревообработки"
  },
  {
    "name": "(Реализация продукции деревообработки)",
    "phone": "+375 (2239) 7-18-42",
    "type": "Сектор сбыта продукции деревообработки"
  },
  {
    "name": "(Реализация продукции деревообработки)",
    "phone": "+375 (2239) 3-64-71",
    "type": "Сектор сбыта продукции деревообработки"
  },
  {
    "name": "",
    "phone": "+375 29 352 25 20",
    "type": "Реализация домов, бань, беседок, клеёного бруса"
  },
  {
    "name": "",
    "phone": "+375 (2239) 7-18-43",
    "type": "Реализация домов, бань, беседок, клеёного бруса"
  },
  {
    "name": "(приемная)",
    "phone": "+375 (2239) 7-17-80",
    "type": "Факс"
  },
  {
    "name": "(отдел сбыта)",
    "phone": "+375 (2239) 7-17-79",
    "type": "Факс"
  },
  {
    "name": "(отдел материально-технического снабжения)",
    "phone": "+375 (2239) 7-17-82",
    "type": "Факс"
  },
  {
    "name": "(служба главного энергетика)",
    "phone": "+375 (2239) 7-18-06",
    "type": "Факс"
  }
]
"""