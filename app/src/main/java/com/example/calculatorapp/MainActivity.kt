package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.calculatorapp.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    var errorFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // View Binding Done
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




    }

    fun onAllClearClick(view: View) {}
    fun onClearClick(view: View) {}
    fun onOperationClick(view: View) {}
    fun onEqualClick(view: View) {}
    fun onDigitClick(view: View) {}


    fun onEqual(){
    }

}