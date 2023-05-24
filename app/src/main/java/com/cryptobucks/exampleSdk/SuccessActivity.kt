package com.cryptobucks.exampleSdk

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cryptobucks.exampleSdk.databinding.ActivitySuccessBinding
import com.cryptobucksapp.paySdk.domain.dtos.SuccessInvoice

class SuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras?.getSerializable(MainActivity.DATA) as SuccessInvoice
        setupBinding(data)
    }

    @SuppressLint("SetTextI18n")
    private fun setupBinding(data: SuccessInvoice) {
        binding.apply {
            tvName.text = data.customerName
            tvBusinessName.text = data.businessName
            tvEmail.text = data.customerEmail
            tvPhone.text = data.customerPhone
            tvNotes.text = data.description
            tvStatus.text = data.status?.name ?: ""
            tvAmount.text = "${data.amount}"
            tvFee.text = "${data.transactionFee}"
            tvTotal.text = "${((data.amount ?: 0.0) + (data.transactionFee ?: 0.0))}"
        }
    }
}