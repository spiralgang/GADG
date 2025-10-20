package com.reaver.codeterminal;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectJavaScript();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData("<!DOCTYPE html><html><head></head><body></body></html>", "text/html", "UTF-8");
    }

    private void injectJavaScript() {
        String js = "(function(){ const id = 'reaver-console'; if (document.getElementById(id)) { const existing = document.getElementById(id); existing.style.display = existing.style.display === 'none' ? 'flex' : 'none'; return; } const container = document.createElement('div'); container.id = id; container.style.cssText = ` position: fixed; bottom: 15px; right: 15px; width: 350px; max-height: 500px; background-color: #111; border: 2px solid #f00; border-radius: 5px; z-index: 99999; display: flex; flex-direction: column; font-family: monospace; color: #fff; box-shadow: 0 0 20px rgba(255, 0, 0, 0.5); `; const header = document.createElement('div'); header.style.cssText = ` padding: 10px; background-color: #333; color: #f00; font-weight: bold; cursor: move; text-align: center; border-bottom: 1px solid #f00; `; header.textContent = 'CODE-REAVER TERMINAL'; container.appendChild(header); const content = document.createElement('div'); content.style.cssText = `padding: 10px; overflow-y: auto; flex-grow: 1;`; container.appendChild(content); const urlInput = document.createElement('input'); urlInput.placeholder = 'Paste Colab ngrok URL here...'; urlInput.style.cssText = `width: 95%; background: #222; color: #0f0; border: 1px solid #333; padding: 5px; margin-bottom: 10px;`; content.appendChild(urlInput); const promptArea = document.createElement('textarea'); promptArea.placeholder = 'Enter your decree...'; promptArea.style.cssText = `width: 95%; height: 100px; background: #222; color: #0f0; border: 1px solid #333; padding: 5px; margin-bottom: 10px;`; content.appendChild(promptArea); const sendButton = document.createElement('button'); sendButton.textContent = 'UNLEASH'; sendButton.style.cssText = `width: 100%; padding: 10px; background: #f00; color: #fff; border: none; cursor: pointer; font-weight: bold;`; content.appendChild(sendButton); const responseArea = document.createElement('pre'); responseArea.style.cssText = `background: #000; padding: 10px; margin-top: 10px; white-space: pre-wrap; word-wrap: break-word; border: 1px dashed #555; height: 150px; overflow-y: auto;`; responseArea.textContent = 'The beast awaits...'; content.appendChild(responseArea); document.body.appendChild(container); sendButton.onclick = () => { const url = urlInput.value.trim(); const prompt = promptArea.value.trim(); if (!url || !prompt) { responseArea.textContent = '!! URL and Decree are mandatory.'; return; } responseArea.textContent = '>> Sending decree to the abyss...'; fetch(`${url}/v1/chat/completions`, { method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({model: 'any', messages: [{role: 'user', content: prompt}]}) }) .then(res => res.json()) .then(data => { responseArea.textContent = data.choices[0].message.content; }) .catch(err => { responseArea.textContent = `!! THE ABYSS REJECTED YOUR DECREE: ${err.message}`; }); }; let isDragging = false; let offset = {x: 0, y: 0}; header.onmousedown = (e) => { isDragging = true; offset.x = e.clientX - container.offsetLeft; offset.y = e.clientY - container.offsetTop; }; document.onmousemove = (e) => { if (!isDragging) return; container.style.left = `${e.clientX - offset.x}px`; container.style.top = `${e.clientY - offset.y}px`; }; document.onmouseup = () => { isDragging = false; }; })();";
        webView.evaluateJavascript(js, null);
    }
}
