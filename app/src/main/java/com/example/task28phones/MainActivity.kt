package com.example.task28phones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task28phones.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var phonesFragment = PhonesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, phonesFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}