package com.peterchege.speechrecognition

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class MainViewModel :ViewModel() {

    private val _outputText = mutableStateOf("")
    val outputText : State<String> = _outputText

    private val _backgroundColor = mutableStateOf<Color>(Color.White)
    val backgroundColor : State<Color> = _backgroundColor


    fun onChangeOutputText(text:String){
        _outputText.value = text

    }

    fun onChangeBackgroundColor(color:Color){
        _backgroundColor.value = color
    }

    fun setBackgroundColor(text:String,context: Context){
        if (text =="blue" || text.contains("blue")){
            _backgroundColor.value = Color.Blue
        }else if(text =="red" || text.contains("red")){
            _backgroundColor.value = Color.Red

        }else{
            Toast.makeText(context,"No color detected",Toast.LENGTH_SHORT).show()
        }
    }




}