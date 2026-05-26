import { useState, useRef, useEffect } from 'react';
import { Wllama } from '@wllama/wllama';
import './App.css';

interface Message {
  id: string;
  sender: 'user' | 'model';
  text: string;
}

function App() {
  const [messages, setMessages] = useState<Message[]>([]);
  const [input, setInput] = useState('');
  const [isLoaded, setIsLoaded] = useState(false);
  const [loadingStatus, setLoadingStatus] = useState<string | null>(null);
  
  const wllamaRef = useRef<Wllama | null>(null);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const handleFileUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    try {
      setLoadingStatus(`Initializing WebAssembly Engine...`);
      
      const wllama = new Wllama({
        // By default, wllama dynamically loads the wasm from its own CDN
        // If it fails, we would need to manually copy it to the public folder.
        // For now, we rely on its internal default unpkg resolution for simplicity.
      });

      setLoadingStatus(`Loading ${file.name} to memory...`);
      await wllama.loadModelFromFile(file); // Or loadModelFromBlob depending on wllama version
      
      wllamaRef.current = wllama;
      setIsLoaded(true);
      setLoadingStatus(null);
      setMessages([{ id: Date.now().toString(), sender: 'model', text: 'Model loaded successfully. How can I help you today?' }]);
    } catch (err: any) {
      console.error(err);
      setLoadingStatus(`Error loading model: ${err.message || 'Unknown error. Check console.'}`);
    }
  };

  const handleSend = async () => {
    if (!input.trim() || !wllamaRef.current) return;
    
    const userMessage: Message = { id: Date.now().toString(), sender: 'user', text: input };
    setMessages((prev) => [...prev, userMessage]);
    setInput('');
    
    // Add an empty assistant message to stream into
    const modelId = (Date.now() + 1).toString();
    setMessages((prev) => [...prev, { id: modelId, sender: 'model', text: '' }]);

    // Format prompt naively or use Wllama built-in chat formatters if available
    // Here we use a basic ChatML / Inst format
    const promptContext = `<|im_start|>user\n${userMessage.text}<|im_end|>\n<|im_start|>assistant\n`;

    try {
      await wllamaRef.current.createCompletion(promptContext, {
        nPredict: 512,
        sampling: { temp: 0.7, top_k: 40, top_p: 0.9 },
        onNewToken: (token, piece, currentText) => {
          setMessages((prev) => prev.map((msg) => {
            if (msg.id === modelId) {
              return { ...msg, text: msg.text + new TextDecoder().decode(piece) };
            }
            return msg;
          }));
        }
      });
    } catch (err: any) {
      console.error(err);
      setMessages((prev) => prev.map((msg) => msg.id === modelId ? { ...msg, text: `[Error: ${err.message}]` } : msg));
    }
  };

  return (
    <div className="app-container">
      <div className="glass-panel main-chat-container">
        
        <header className="chat-header">
          <div className="header-title">
            <h1>DeepwaveSLM</h1>
            <span className="badge">WebAssembly</span>
          </div>
          <p className="subtitle">100% Offline, Privacy-Preserving GenAI</p>
        </header>

        {!isLoaded ? (
          <div className="setup-container">
            <div className="upload-box">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="upload-icon">
                <path strokeLinecap="round" strokeLinejoin="round" d="M12 16.5V9.75m0 0l3 3m-3-3l-3 3M6.75 19.5a4.5 4.5 0 01-1.41-8.775 5.25 5.25 0 0110.233-2.33 3 3 0 013.758 3.848A3.752 3.752 0 0118 19.5H6.75z" />
              </svg>
              <h2>Load Model Context</h2>
              <p>Select a <code>.gguf</code> file from your computer to run natively.</p>
              
              <label className="file-upload-btn">
                <span>Browse Local Files</span>
                <input type="file" accept=".gguf" onChange={handleFileUpload} style={{display: 'none'}} />
              </label>

              {loadingStatus && (
                <div className="loading-indicator">
                  <div className="spinner"></div>
                  <p>{loadingStatus}</p>
                </div>
              )}
            </div>
          </div>
        ) : (
          <>
            <div className="messages-area">
              {messages.map((msg) => (
                <div key={msg.id} className={`message-wrapper \${msg.sender}`}>
                  <div className={`message-bubble \${msg.sender}`}>
                    {msg.text}
                  </div>
                </div>
              ))}
              <div ref={messagesEndRef} />
            </div>

            <div className="input-area">
              <input 
                type="text" 
                className="chat-input"
                placeholder="Type your message..." 
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onKeyDown={(e) => e.key === 'Enter' && handleSend()}
              />
              <button className="send-btn" onClick={handleSend} disabled={!input.trim()}>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M3.478 2.404a.75.75 0 0 0-.926.941l2.432 7.905H13.5a.75.75 0 0 1 0 1.5H4.984l-2.432 7.905a.75.75 0 0 0 .926.94 60.519 60.519 0 0 0 18.445-8.986.75.75 0 0 0 0-1.218A60.517 60.517 0 0 0 3.478 2.404Z" />
                </svg>
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default App;
