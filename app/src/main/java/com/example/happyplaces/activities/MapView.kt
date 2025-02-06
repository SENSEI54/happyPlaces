package com.example.happyplaces.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happyplaces.R
import com.example.happyplaces.databinding.ActivityMapViewBinding

class MapView : AppCompatActivity() {
    private lateinit var binding:ActivityMapViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMapViewBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
    }
}