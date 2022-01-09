package ru.olgabelchenko.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.olgabelchenko.androidcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val buttonList: MutableList<Button> = mutableListOf()

    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var currentOperation = ""
    private var isNumberButtonPressed = false
    private var isSecondNumberStarting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editText.isFocusableInTouchMode = false
        binding.editText.isFocusable = false

        buttonList.add(binding.button0)
        buttonList.add(binding.button1)
        buttonList.add(binding.button2)
        buttonList.add(binding.button3)
        buttonList.add(binding.button4)
        buttonList.add(binding.button5)
        buttonList.add(binding.button6)
        buttonList.add(binding.button7)
        buttonList.add(binding.button8)
        buttonList.add(binding.button9)

        for (number in 0..9) {
            buttonList[number].setOnClickListener {
                if (isSecondNumberStarting) {
                    binding.editText.setText("0")
                    isSecondNumberStarting = false
                }
                val currentText = binding.editText.text.toString()
                if (number != 0 || number == 0 && currentText != "0") {
                    var newText = currentText + number
                    if (newText[0] == '0' && newText[1] != '.') {
                        newText = newText.substring(1)
                    } else if (newText[0] == '-' && newText[1] == '0' && newText[3] != '.') {
                        newText = "-" + newText.substring(2)
                    }
                    binding.editText.setText(newText)
                    isNumberButtonPressed = true
                }
            }
        }

        binding.dotButton.setOnClickListener {
            val currentText = binding.editText.text.toString()
            if (!currentText.contains('.')) {
                val newText = "$currentText."
                binding.editText.setText(newText)
            }
        }

        binding.clearButton.setOnClickListener {
            binding.editText.setText("0")
            currentOperation = ""
            isNumberButtonPressed = false
        }

        binding.addButton.setOnClickListener { operationButtonClicked("add") }
        binding.subtractButton.setOnClickListener { operationButtonClicked("subtract") }
        binding.multiplyButton.setOnClickListener { operationButtonClicked("multiply") }
        binding.divideButton.setOnClickListener { operationButtonClicked("divide") }

        binding.equalButton.setOnClickListener {
            secondNumber = binding.editText.text.toString().toDouble()
            val resultText = when (currentOperation) {
                "add" -> "${firstNumber + secondNumber}"
                "subtract" -> {
                    if (isNumberButtonPressed) {
                        "${firstNumber - secondNumber}"
                    } else {
                        (-0).toString()
                    }
                }
                "multiply" -> "${firstNumber * secondNumber}"
                else -> if (secondNumber != 0.0) {
                    "${firstNumber / secondNumber}"
                } else {
                    "0"
                }
            }
            binding.editText.setText(resultText)
        }
    }

    private fun operationButtonClicked(operation: String) {
        currentOperation = operation
        firstNumber = binding.editText.text.toString().toDouble()
        isSecondNumberStarting = true
    }


}