# DeepwaveSLM7 LocalLLM

This is a modern, reliable, on-device Android LLM application built natively using `llama.cpp` JNI bindings. It is designed to run any raw `.gguf` file without requiring API calls or internet connection.

## How to use

1. **Open the Project:**
   Open Android Studio and import the `/Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM7_LocalLLM` folder as an Android Project.

2. **Wait for Gradle Sync:**
   Android Studio will automatically download the correct NDK and Android SDK required to compile `llama.cpp` natively for your ARM Android device.

3. **Deploy to Device:**
   Connect an Android device with at least 8GB of RAM. Click the green "Run" arrow in Android Studio.

4. **Load a Model:**
   * Download a tiny GGUF model (e.g., `Gemma-2B-Q4_K_M.gguf` or `Qwen-1.5B-Q4_K_M.gguf`) onto your Android device's storage.
   * Open the app. It currently has a Floating Action Button asking you to select a `.gguf` file.
   * Select your downloaded `.gguf` file via the secure Android File Picker.

5. **Chat!**
   The app will parse the GGUF metadata, load the model onto the device CPU/GPU utilizing Android performance tools, and allow you to chat natively offline.

Enjoy your privacy-preserving on-device LLM!
