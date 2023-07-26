package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var inputKilograms: EditText
    private lateinit var inputGrams: EditText
    private lateinit var inputTonnes: EditText
    private lateinit var inputPounds: EditText

    private val decimalFormat = DecimalFormat("#.##")
    private var isUpdatingText = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputKilograms = findViewById(R.id.inputKilograms)
        inputGrams = findViewById(R.id.inputGrams)
        inputPounds = findViewById(R.id.inputPounds)
        inputTonnes = findViewById(R.id.inputTonnes)

        inputKilograms.addTextChangedListener(createTextWatcher(inputKilograms))
        inputGrams.addTextChangedListener(createTextWatcher(inputGrams))
        inputPounds.addTextChangedListener(createTextWatcher(inputPounds))
        inputTonnes.addTextChangedListener(createTextWatcher(inputTonnes))
    }

    private fun createTextWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isUpdatingText) {
                    updateConversions(editText)
                }
            }
        }
    }

    private fun updateConversions(source: EditText) {
        val kilograms = inputKilograms.text.toString().toDoubleOrNull() ?: 0.0

        isUpdatingText = true

        when (source) {
            inputKilograms -> {
                updateEditText(inputGrams, kilograms * 1000)
                updateEditText(inputPounds, kilograms * 2.20462)
                updateEditText(inputTonnes, kilograms / 1000)
            }
            inputGrams -> {
                val grams = inputGrams.text.toString().toDoubleOrNull() ?: 0.0
                updateEditText(inputKilograms, grams / 1000)
                updateEditText(inputPounds, grams / 453.592)
                updateEditText(inputTonnes, grams / 1_000_000)
            }
            inputPounds -> {
                val pounds = inputPounds.text.toString().toDoubleOrNull() ?: 0.0
                updateEditText(inputKilograms, pounds / 2.20462)
                updateEditText(inputGrams, pounds * 453.592)
                updateEditText(inputTonnes, pounds / 2204.62)
            }
            inputTonnes -> {
                val tonnes = inputTonnes.text.toString().toDoubleOrNull() ?: 0.0
                updateEditText(inputKilograms, tonnes * 1000)
                updateEditText(inputGrams, tonnes * 1_000_000)
                updateEditText(inputPounds, tonnes * 2204.62)
            }
        }

        isUpdatingText = false
    }

    private fun updateEditText(editText: EditText, value: Double) {
        editText.setText(decimalFormat.format(value))
    }
}