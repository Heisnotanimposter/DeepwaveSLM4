package com.example.deepwaveslm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.deepwaveslm.TFLiteModel
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var tfliteModel1: TFLiteModel? = null
    private var tfliteModel2: TFLiteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            tfliteModel1 = TFLiteModel(this, "model1.tflite")
            tfliteModel2 = TFLiteModel(this, "model2.tflite")

            val input1 = floatArrayOf(1.0f, 2.0f) // Example input for model1
            val output1 = tfliteModel1!!.predict(input1)
            Log.d("TFLiteOutput1", "Prediction from model1: ${output1[0]}")

            val input2 = floatArrayOf(3.0f, 4.0f) // Example input for model2
            val output2 = tfliteModel2!!.predict(input2)
            Log.d("TFLiteOutput2", "Prediction from model2: ${output2[0]}")

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tfliteModel1?.close()
        tfliteModel2?.close()
    }
}