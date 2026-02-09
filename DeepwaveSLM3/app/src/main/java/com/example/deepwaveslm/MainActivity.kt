package com.example.deepwaveslm

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var tfliteModel1: TFLiteModel? = null
    private var tfliteModel2: TFLiteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusView = findViewById<TextView>(R.id.statusText)

        try {
            // Try loading model1 first, then model2 (graceful when assets are missing)
            tfliteModel1 = try {
                TFLiteModel(this, "model1.tflite")
            } catch (_: IOException) {
                null
            }
            if (tfliteModel1 != null) {
                val input1 = floatArrayOf(1.0f, 2.0f)
                val output1 = tfliteModel1!!.predict(input1)
                Log.d("TFLiteOutput1", "Prediction from model1: ${output1[0]}")
                statusView.text = getString(R.string.model_loaded, "model1", output1[0].toString())
            } else {
                tfliteModel2 = try {
                    TFLiteModel(this, "model2.tflite")
                } catch (_: IOException) {
                    null
                }
                if (tfliteModel2 != null) {
                    val input2 = floatArrayOf(3.0f, 4.0f)
                    val output2 = tfliteModel2!!.predict(input2)
                    Log.d("TFLiteOutput2", "Prediction from model2: ${output2[0]}")
                    statusView.text = getString(R.string.model_loaded, "model2", output2[0].toString())
                } else {
                    statusView.text = getString(R.string.no_models_hint)
                    Toast.makeText(this, R.string.no_models_hint, Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "TFLite error", e)
            statusView.text = getString(R.string.model_error, e.message ?: "")
            Toast.makeText(this, R.string.model_error_toast, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tfliteModel1?.close()
        tfliteModel2?.close()
    }
}