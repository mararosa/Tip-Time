package com.estudos.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.estudos.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }


    private fun calculateTip() {
        // Get the decimal value from the cost of service text field
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTipFormatted(0.0)
            return
        }
        val tipPercentage = getPercentage()
        var tip = tipPercentage * cost

        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        displayTipFormatted(tip)
    }

    private fun displayTipFormatted(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun getPercentage(): Double {
        return when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
    }
}

//private fun getCost() {
//    val stringInTextField = binding.costOfServiceEditText.text.toString()
//    val cost = stringInTextField.toDoubleOrNull()
//    if (cost == null || cost == 0.0) {
//        displayTipFormatted(0.0)
//        return
//    }
//}
//private fun roundUpTipAmount(tip: Double) {
//    val roundUp = binding.roundUpSwitch.isChecked
//    if (roundUp) {
//        tip = kotlin.math.ceil(tip)
//    }
//}