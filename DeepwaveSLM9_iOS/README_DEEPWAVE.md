# DeepwaveSLM9 iOS Native (llama.swiftui)

This is the native iOS version of DeepwaveSLM, built using SwiftUI and the `llama.cpp` native core. It utilizes Apple's **Metal** framework for high-performance on-device inference on iPhones and iPads.

## How to Build and Run

Since I (the AI) do not have the iOS SDK and Xcode installed in this terminal sandbox, you will need to perform the final build steps in Xcode on your Mac:

1. **Build the XCFramework:**
   Open your terminal and run the following command to compile the C++ core into an Apple-compatible framework:
   ```bash
   cd /Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM7_LocalLLM/llama_cpp_src
   ./build-xcframework.sh
   ```

2. **Open the Project:**
   Open the `/Users/seungwonlee/DeepwaveSLM4/DeepwaveSLM9_iOS/llama.swiftui.xcodeproj` file in **Xcode**.

3. **Link the Framework:**
   In Xcode, ensure the generated `llama.xcframework` (found in the `build-apple` folder of the source) is linked in the "Frameworks, Libraries, and Embedded Content" section of the project settings.

4. **Deploy:**
   Connect your iPhone/iPad and click the "Run" button in Xcode.

## Adding Models
- You can add `.gguf` models directly to the app via the **Files** app on your iPhone once the app is installed.
- Or, you can drag and drop tiny models into the `Models` folder in the Xcode project before building.

Enjoy your native iOS LLM experience!
