package com.cryptobucks.exampleSdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cryptobucks.exampleSdk.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvErrorSdk.text = intent.extras?.getString(MainActivity.ERROR) ?: "Bad Error"
    }
}