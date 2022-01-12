package ru.olgabelchenko.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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

        with (binding) {
        editText.isFocusableInTouchMode = false
        editText.isFocusable = false

        buttonList.add(button0)
        buttonList.add(button1)
        buttonList.add(button2)
        buttonList.add(button3)
        buttonList.add(button4)
        buttonList.add(button5)
        buttonList.add(button6)
        buttonList.add(button7)
        buttonList.add(button8)
        buttonList.add(button9)


            for (number in 0..9) {
                buttonList[number].setOnClickListener {
                    if (isSecondNumberStarting) {
                        editText.setText("0")
                        isSecondNumberStarting = false
                    }
                    val currentText = editText.text.toString()
                    if (number != 0 || number == 0 && currentText != "0") {
                        var newText = currentText + number
                        if (newText[0] == '0' && newText[1] != '.') {
                            newText = newText.substring(1)
                        } else if (newText.length >= 3 && newText[0] == '-' && newText[1] == '0' && newText[2] != '.') {
                            newText = "-" + newText.substring(2)
                        }
                        editText.setText(newText)
                        isNumberButtonPressed = true
                    }
                }
            }

            dotButton.setOnClickListener {
                val currentText = editText.text.toString()
                if (!currentText.contains('.')) {
                    val newText = "$currentText."
                    editText.setText(newText)
                }
            }

            clearButton.setOnClickListener {
                editText.setText("0")
                currentOperation = ""
                isNumberButtonPressed = false
            }

            addButton.setOnClickListener { operationButtonClicked("add") }
            subtractButton.setOnClickListener { operationButtonClicked("subtract") }
            multiplyButton.setOnClickListener { operationButtonClicked("multiply") }
            divideButton.setOnClickListener { operationButtonClicked("divide") }

            equalButton.setOnClickListener {
                secondNumber = editText.text.toString().toDouble()
                val resultText = when (currentOperation) {
                    "add" -> "${firstNumber + secondNumber}"
                    "subtract" -> "${firstNumber - secondNumber}"
                    "multiply" -> "${firstNumber * secondNumber}"
                    "divide" -> if (secondNumber != 0.0) {
                        "${firstNumber / secondNumber}"
                    } else {
                        Toast
                            .makeText(this@MainActivity, "ERROR! Division by zero!", Toast.LENGTH_SHORT)
                            .show()
                        "0"
                    }
                    else -> editText.text.toString()
                }
                editText.setText(resultText)
            }
        }
    }

    private fun operationButtonClicked(operation: String) {
        with (binding) {
            if (operation == "subtract" && !isNumberButtonPressed) {
                editText.setText("-0")
            } else {
                currentOperation = operation
                firstNumber = editText.text.toString().toDouble()
                editText.setText("0")
                isSecondNumberStarting = true
            }
        }
    }


}