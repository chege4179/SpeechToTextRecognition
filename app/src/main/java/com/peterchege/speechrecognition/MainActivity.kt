package com.peterchege.speechrecognition

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.peterchege.speechrecognition.ui.theme.SpeechRecognitionTheme
import java.util.*


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()
        setContent {
            SpeechRecognitionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(viewModel.backgroundColor.value),

                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Speech to Text",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                })
                        }) {
                        val audioPermissionState = rememberPermissionState(
                            Manifest.permission.RECORD_AUDIO
                        )
                        when (audioPermissionState.status) {
                            PermissionStatus.Granted -> {
                                SpeechToText(getSpeechInput = {
                                    startSpeechToText(
                                        viewModel = viewModel
                                        ,ctx = it,finished = {

                                        })
                                }, viewModel = viewModel)

//
                            }
                            is PermissionStatus.Denied -> {
                                Column {
                                    Text(
                                        text = "Permission Denied"
                                    )
                                    Button(onClick = { audioPermissionState.launchPermissionRequest() }) {
                                        Text("Request permission")
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun getSpeechInput(context: Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")
            startActivityForResult(this, intent, 101, null)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // on below line we are checking if the request
        // code is same and result code is ok
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val outputText = result?.get(0).toString()
            Log.e("Speech text", outputText)
            //viewModel.onChangeOutputText(outputText)


        }
    }

}






