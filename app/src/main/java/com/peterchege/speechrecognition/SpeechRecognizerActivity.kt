package com.peterchege.speechrecognition

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract

import java.util.*

class SpeechRecognizerActivity : ActivityResultContract<Int, String?>() {
    override fun createIntent(context: Context, ringtoneType: Int):Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something good")
        //ActivityCompat.startActivityForResult(context, intent, 101, null)

        return intent
    }
    override fun parseResult(resultCode: Int, data: Intent?) : String? {
        if (resultCode== 101) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val outputText = result?.get(0).toString()
            Log.e("Speech text", outputText)


            return outputText
        }else{
            return null
        }

    }

}