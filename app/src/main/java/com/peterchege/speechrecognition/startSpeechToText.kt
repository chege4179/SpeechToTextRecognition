package com.peterchege.speechrecognition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.ui.graphics.Color

fun startSpeechToText(viewModel: MainViewModel, ctx: Context, finished: ()-> Unit) {
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(ctx)
    val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    speechRecognizerIntent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
    )

    // Optionally I have added my mother language
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US")

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(bundle: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(v: Float) {}
        override fun onBufferReceived(bytes: ByteArray?) {}
        override fun onEndOfSpeech() {
            finished()
            // changing the color of your mic icon to
            // gray to indicate it is not listening or do something you want
        }

        override fun onError(i: Int) {}

        override fun onResults(bundle: Bundle) {
            val result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (result != null) {
                viewModel.onChangeOutputText(text = result[0])

                viewModel.setBackgroundColor(text = result[0],context = ctx)
                if (result[0] =="blue" || result[0].contains("blue")){
                    viewModel.textToSpeech(context = ctx, speechText = "Here is the blue screen")

                }else if(result[0] =="red" || result[0].contains("red")){
                    viewModel.textToSpeech(context = ctx, speechText = "Here is the red screen")

                }else{
                    viewModel.textToSpeech(context = ctx, speechText = "No color red or blue mentioned")
                }

            }
        }

        override fun onPartialResults(bundle: Bundle) {}
        override fun onEvent(i: Int, bundle: Bundle?) {}

    })
    speechRecognizer.startListening(speechRecognizerIntent)
}


