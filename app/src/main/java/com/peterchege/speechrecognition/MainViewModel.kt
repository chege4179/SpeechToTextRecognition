package com.peterchege.speechrecognition

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel :ViewModel() {

    private val _outputText = mutableStateOf("")
    val outputText : State<String> = _outputText

    private val _backgroundColor = mutableStateOf<Color>(Color.White)
    val backgroundColor : State<Color> = _backgroundColor

    private  var  textToSpeech:TextToSpeech? = null


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

    fun textToSpeech(context: Context,speechText:String){

        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.US
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        speechText,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }

        }
    }




}