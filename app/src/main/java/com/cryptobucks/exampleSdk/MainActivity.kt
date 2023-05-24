package com.cryptobucks.exampleSdk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.cryptobucks.exampleSdk.databinding.ActivityMainBinding
import com.cryptobucksapp.paySdk.data.contracts.CryptobucksPaySdk
import com.cryptobucksapp.paySdk.data.utils.Result.Error
import com.cryptobucksapp.paySdk.data.utils.Result.Success
import com.cryptobucksapp.paySdk.domain.dtos.InvoiceRequest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val DATA = "data"
        const val ERROR = "error"
    }

    private var customerName = ""
    private var customerEmail = ""
    private var customerPhone = ""
    private var businessName: String? = null
    private var amount = 0.0
    private var description = ""

    private val apiKey = "CHANGE ME"

    private val cryptobucksSdk = registerForActivityResult(CryptobucksPaySdk(apiKey)) { result ->
        when (result) {
            is Success -> {
                startActivity(Intent(this@MainActivity, SuccessActivity::class.java).apply {
                    putExtra(DATA, result.data)
                })
            }

            is Error -> {
                startActivity(Intent(this@MainActivity, ErrorActivity::class.java).apply {
                    putExtra(ERROR, result.error)
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBinding()
    }

    private fun setupBinding() {
        binding.apply {

            etCustomerName.addTextChangedListener {
                customerName = it.toString()
                validateBtn()
            }

            etCustomerEmail.addTextChangedListener {
                customerEmail = it.toString()
                validateBtn()
            }

            etCustomerPhone.addTextChangedListener {
                customerPhone = it.toString()
                validateBtn()
            }

            etBusinessName.addTextChangedListener {
                businessName = it.toString()
                validateBtn()
            }

            etAmount.addTextChangedListener {
                amount = it.toString().toDouble()
                validateBtn()
            }

            etDescription.addTextChangedListener {
                description = it.toString()
                validateBtn()
            }

            btPay.setOnClickListener {
                createRequest()
            }
        }
    }

    private fun createRequest() {
        cryptobucksSdk.launch(
            InvoiceRequest(
                taxRate = 0.0,
                customerPhone = customerPhone,
                amount = amount,
                customerEmail = customerEmail,
                businessName = businessName,
                description = description,
                tipRate = 0.0,
                customerName = customerName
            )
        )
    }

    private fun validateBtn() {
        binding.btPay.isEnabled =
            customerName.isNotEmpty() && customerEmail.isNotEmpty() && customerPhone.isNotEmpty() && amount != 0.0 && description.isNotEmpty()
    }
}