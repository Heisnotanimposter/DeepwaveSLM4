# DeepwaveSLM (Small Language Model on Device)

Welcome to **DeepwaveSLM**, a privacy-focused project for running quantized Small Language Models (SLMs) completely on-device without any API calls, remote servers, or internet connection.

---

## 📁 Repository Directory Structure

This repository contains multiple platform implementations, documentation, and legacy code folders.

### 🌟 Active Multi-Platform Implementations

*   **[DeepwaveSLM7_LocalLLM](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM7_LocalLLM)**: **Android Native App**. Uses highly optimized C++ `llama.cpp` JNI bindings for running quantized `.gguf` models locally on device CPUs/GPUs.
*   **[DeepwaveSLM8_Web](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM8_Web)**: **PC Web Browser App**. Built using React, TypeScript, and Vite. Utilizes `@wllama/wllama` WebAssembly (WASM) to load and run `.gguf` models directly in the Chrome/Edge browser sandbox client-side.
*   **[DeepwaveSLM9_iOS](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM9_iOS)**: **iOS Native App**. Built with SwiftUI, utilizing the `llama.cpp` Apple target and the **Metal** framework for GPU acceleration on iPhones and iPads.

### 📄 Documentation Files
*   **[DeepwaveSLM.pdf](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM.pdf)**: Project report and system design specifications.
*   **[DeepwaveSLM1.pptx](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM1.pptx)**: Project presentation slide deck.

### 🚫 Legacy / Abandoned Attempts
*   **[deepwaveSLM](file:///Users/seungwonlee/DeepwaveSLM4/deepwaveSLM)**, **[DeepwaveSLM2](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM2)**, **[DeepwaveSLM3](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM3)**, **[DeepwaveSLM5](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM5)**, **[DeepwaveSLM6](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM6)**:
    These directories represent deprecated iterations using Python (Chaquopy on Android). They were abandoned due to high overhead, dependency version mismatches, and general unreliability, and have been succeeded by the new native implementations.

---

## 🤖 Where are the Models? (Model Placement & Selection)

Because LLM weight files are extremely large (typically ranging from 1GB to 5GB+), the actual model weights are **not stored** directly in this git repository to prevent bloated downloads.

Here is how each platform loads/acquires the model:

### 1. Android App ([DeepwaveSLM7_LocalLLM](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM7_LocalLLM))
*   **How it loads:** Uses a native File Picker.
*   **Where to place/download:**
    1. Download any `.gguf` quantized chat model (e.g., [Qwen-1.5B-Chat-GGUF](https://huggingface.co/Qwen/Qwen1.5-1.5B-Chat-GGUF), `Gemma-2B-it-GGUF`, or `TinyLlama-1.1B-Chat-v1.0-GGUF`) directly onto your Android device's internal storage (e.g., your Downloads folder).
    2. Open the app, tap the Floating Action Button, select the `.gguf` file using the Android system file picker, and it will load and begin chat generation locally.
*   *Note:* The files inside [llama_cpp_src/models](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM7_LocalLLM/llama_cpp_src/models) (such as `ggml-vocab-*.gguf`) are vocabulary tables used during development/compilation and are **not** runnable model weights.

### 2. PC Web Browser App ([DeepwaveSLM8_Web](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM8_Web))
*   **How it loads:** Uses the HTML5 File Reader API.
*   **Where to place/download:**
    1. Download any `.gguf` model (we recommend small ones like `Qwen-0.5B` or `TinyLlama-1.1B` for faster browser loading) to your local computer.
    2. Start the web app (see instructions below) and open it.
    3. Click **Browse Local Files** on the UI, select your local `.gguf` file, and Wllama will load it directly into browser memory (completely offline, client-side).

### 3. iOS App ([DeepwaveSLM9_iOS](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM9_iOS))
*   **How it loads:** Downloads from Hugging Face or loads from the app bundle/Files App.
*   **Where to place/download:**
    *   **Option A (Direct Download):** The app interface lists several popular small models (TinyLlama-1.1B, Phi-2, Mistral-7B) that you can download directly to the device from Hugging Face with one click.
    *   **Option B (App Bundle):** Before compiling in Xcode, you can drag and drop a tiny model into the `Models` group folder in Xcode, naming it `ggml-model.gguf`.
    *   **Option C (Files App):** Load `.gguf` files directly through the Apple Files App.

---

## 🚀 How to Try and Run

### 📲 Running Android Native
1. Open **Android Studio**.
2. Select **Open** and select the folder `[DeepwaveSLM7_LocalLLM](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM7_LocalLLM)`.
3. Wait for Gradle sync (Android Studio will automatically download NDK/SDK dependencies to compile `llama.cpp` natively).
4. Run the project on an Android device with at least 8GB of RAM.

### 💻 Running PC Web Browser
1. In your terminal, navigate to the web directory:
   ```bash
   cd /Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM8_Web
   ```
2. Clear any corrupt locks and install dependencies:
   ```bash
   rm -rf node_modules package-lock.json
   npm install
   ```
3. Run the development server:
   ```bash
   npm run dev
   ```
4. Open the local address in Chrome (e.g., `http://localhost:5173`) and load your local model!

### 🍏 Running iOS Native
1. Build the native Apple framework:
   ```bash
   cd /Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM7_LocalLLM/llama_cpp_src
   ./build-xcframework.sh
   ```
2. Open `[llama.swiftui.xcodeproj](file:///Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM9_iOS/llama.swiftui.xcodeproj)` in **Xcode**.
3. Link the compiled `llama.xcframework` in Xcode target settings.
4. Deploy to your Apple device.
