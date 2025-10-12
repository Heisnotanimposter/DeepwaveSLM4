package com.example.deepwaveslm

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {
    private lateinit var bertQaModule: PyObject
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var chatLayout: LinearLayout
    private lateinit var scrollViewChat: ScrollView
    private lateinit var editTextQuery: EditText
    private lateinit var buttonSend: Button
    private var selectedModel: String = "BERTQACustom.tflite"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        val py = Python.getInstance()
        bertQaModule = py.getModule("bert_qa")

        drawerLayout = findViewById(R.id.drawer_layout)
        chatLayout = findViewById(R.id.chatLayout)
        scrollViewChat = findViewById(R.id.scrollViewChat)
        editTextQuery = findViewById(R.id.input_edit_text)
        buttonSend = findViewById(R.id.send_button)

        val openDrawerButton = findViewById<Button>(R.id.open_drawer_button)
        val buttonBERTQACustom = findViewById<Button>(R.id.buttonBERTQACustom)
        val buttonBERTQAStarter = findViewById<Button>(R.id.buttonBERTQAStarter)
        val buttonFalcon1B = findViewById<Button>(R.id.buttonFalcon1B)
        val buttonPhi2B = findViewById<Button>(R.id.buttonPhi2B)
        val buttonAutocomplete = findViewById<Button>(R.id.buttonAutocomplete)

        openDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(findViewById(R.id.left_drawer))
        }

        buttonBERTQACustom.setOnClickListener {
            selectedModel = "BERTQACustom.tflite"
            drawerLayout.closeDrawers()
        }
        buttonBERTQAStarter.setOnClickListener {
            selectedModel = "BERTQA_Starter_with_Google.tflite"
            drawerLayout.closeDrawers()
        }
        buttonFalcon1B.setOnClickListener {
            selectedModel = "falcon1B_cpu.bin"
            drawerLayout.closeDrawers()
        }
        buttonPhi2B.setOnClickListener {
            selectedModel = "phi2B_cpu.bin"
            drawerLayout.closeDrawers()
        }
        buttonAutocomplete.setOnClickListener {
            selectedModel = "autocomplete.tflite"
            drawerLayout.closeDrawers()
        }

        buttonSend.setOnClickListener {
            val userQuery = editTextQuery.text.toString()
            if (userQuery.isNotBlank()) {
                addChatMessage("User: $userQuery")
                editTextQuery.text.clear()

                val context = "Provide some context here if necessary"
                val answer = bertQaModule.callAttr("get_model_response", selectedModel, assets, userQuery, context).toString()
                addChatMessage("Model: $answer")

                scrollViewChat.post { scrollViewChat.fullScroll(View.FOCUS_DOWN) }
            }
        }
    }

    private fun addChatMessage(message: String) {
        val textView = TextView(this)
        textView.text = message
        chatLayout.addView(textView)
    }
}