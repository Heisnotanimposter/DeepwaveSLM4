import android.R
import android.os.Bundle
import android.util.Log
import com.chaquo.python.PyObject
import com.chaquo.python.Python

class MainActivity : AppCompatActivity() {
    private var bertQaModule: PyObject? = null
    private var interpreter: PyObject? = null
    private var inputDetails: PyObject? = null
    private var outputDetails: PyObject? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val py: Python = Python.getInstance()
        bertQaModule = py.getModule("bert_qa")

        // Load dataset
        val dataset: PyObject =
            bertQaModule.callAttr("load_dataset", "path/to/CertificationQADataset.json")

        // Prepare data
        val inputs: PyObject = bertQaModule.callAttr("prepare_data", dataset)
        val startPositions: PyObject = inputs.get("start_positions")
        val endPositions: PyObject = inputs.get("end_positions")

        // Train model
        val model: PyObject = bertQaModule.callAttr(
            "train_model",
            bertQaModule.callAttr("model"),
            inputs,
            startPositions,
            endPositions
        )

        // Save model as TFLite
        bertQaModule.callAttr("save_model_as_tflite", model, "path/to/BERTQACustom.tflite")

        // Load TFLite model
        interpreter = bertQaModule.callAttr("tf.lite.Interpreter", "path/to/BERTQACustom.tflite")
        interpreter.callAttr("allocate_tensors")
        inputDetails = interpreter.callAttr("get_input_details")
        outputDetails = interpreter.callAttr("get_output_details")

        // Test the model
        testModel()
    }

    private fun testModel() {
        val question = "What is BERT?"
        val context = "BERT is a model developed by Google."
        val answer: String = bertQaModule.callAttr(
            "get_answer",
            interpreter,
            inputDetails,
            outputDetails,
            question,
            context
        ).toString()
        Log.d("BERT_ANSWER", answer)
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DeepwaveSLMTheme {
        Greeting("Android")
    }
}