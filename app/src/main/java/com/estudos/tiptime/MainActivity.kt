package com.estudos.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.estudos.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListeners()
    }


    private fun setClickListeners() {
        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
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
            R.id.option_twenty_percent -> TIP_TWENTY_PERCENT
            R.id.option_eighteen_percent -> TIP_EIGHTEEN_PERCENT
            else -> TIP_FIFTEEN_PERCENT
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    companion object {
        const val TIP_TWENTY_PERCENT = 0.20
        const val TIP_EIGHTEEN_PERCENT = 0.18
        const val TIP_FIFTEEN_PERCENT = 0.15
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