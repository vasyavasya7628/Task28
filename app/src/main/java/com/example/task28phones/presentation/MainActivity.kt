package com.example.task28phones.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task28phones.R
import com.example.task28phones.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}