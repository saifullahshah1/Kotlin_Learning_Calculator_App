package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding
import kotlin.math.exp


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var errorFlag = false
    var decimalFlag = true
    var operatorFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // View Binding Done
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onAllClearClick(view: View) {
        binding.expressionText.text = ""
        binding.resultText.text = ""
        binding.resultText.visibility = View.GONE
    }

    fun onClearClick(view: View) {
        binding.expressionText.text = ""
    }

    fun onBackSpaceClick(view: View) {
        val textLength = binding.expressionText.length()

        if(textLength > 0){
            binding.expressionText.text = binding.expressionText.text.subSequence(0, textLength-1)
        }
    }

    fun onOperationClick(view: View) {
        if (view is Button) {
            if (operatorFlag) {
                binding.expressionText.append(view.text)
                operatorFlag = false
                decimalFlag = true
            }
        }
    }

    fun onDigitClick(view: View) {

        if (view is Button) {
            if (view.text == ".") {
                if (decimalFlag) {
                    binding.expressionText.append(view.text)
                }
                decimalFlag = false
            } else {
                binding.expressionText.append(view.text)
            }
            operatorFlag = true
        }
    }

    fun onEqualClick(view: View) {
        binding.resultText.text = dmasCalculaltion()
        binding.resultText.visibility = View.VISIBLE
        binding.expressionText.text = ""
    }

    private fun dmasCalculaltion(): String {
        var expressionList = expressionCalculation()
        if (expressionList.isEmpty()) {
            return ""
        }

        if (expressionList.contains('/') || expressionList.contains('x')) {
            expressionList = divisionMultiplicationFtn(expressionList)
        }
        if (expressionList.isEmpty()) {
            return ""
        }

        var calculatedResult = additionSubtractionFtn(expressionList)
        return calculatedResult.toString()

        return ""
    }

    private fun expressionCalculation(): MutableList<Any> {

        var expressionList = mutableListOf<Any>()
        var currentSubstring = ""

        for (currentChar in binding.expressionText.text) {
            if (currentChar.isDigit() || currentChar == '.') {
                currentSubstring += currentChar
            } else {
                if (decimalFlag) {
                    expressionList.add(currentSubstring.toFloat())
                } else {
                    expressionList.add(currentSubstring.toInt())
                }
                currentSubstring = ""
                expressionList.add(currentChar)
            }
        }

        if (currentSubstring != "") {
            if (decimalFlag) {
                expressionList.add(currentSubstring.toFloat())
            } else {
                expressionList.add(currentSubstring.toInt())
            }
        }

        return expressionList
    }

    private fun divisionMultiplicationFtn(inputList: MutableList<Any>): MutableList<Any> {
        var newExpressionList = mutableListOf<Any>()
        var listSize = inputList.size

        for (index in inputList.indices) {
            if (inputList[index] is Char && index < listSize && index != inputList.lastIndex) {
                val curOperator = inputList[index]
                val prevNumber = inputList[index - 1] as Float
                val nextNumber = inputList[index + 1] as Float

                when (curOperator) {
                    'x' -> {
                        newExpressionList.add(prevNumber * nextNumber)
                        listSize = index + 1
                    }

                    '/' -> {
                        newExpressionList.add(prevNumber / nextNumber)
                        listSize = index + 1
                    }

                    else -> {

                        newExpressionList.add(prevNumber) // nhi samaj ayah
                        newExpressionList.add(curOperator) // nhi samaj ayah
                    }
                }
            }

            if (index > listSize) { // nhi samaj ayah
                newExpressionList.add(inputList[index]) // nhi samaj ayah
            }
        }

        return newExpressionList
    }


    private fun additionSubtractionFtn(inputList: MutableList<Any>): Float {
        var expressionList = inputList
        var calculatedResult = expressionList[0] as Float

        for (index in expressionList.indices) {
            if (expressionList[index] is Char && index != expressionList.lastIndex) {
                val curOperator = expressionList[index]
                val nextNumber = expressionList[index + 1] as Float

                if (curOperator == '+') {
                    calculatedResult += nextNumber
                }
                if (curOperator == '-') {
                    calculatedResult -= nextNumber
                }
            }
        }

        return calculatedResult
    }


}