# ==============================================================================
#  ONE-CELL COLAB INFERNO: UNCENSORED MODEL DEPLOYMENT
# ==============================================================================
# This script does one thing: it summons an uncensored AI and gives you the key.
# It installs the necessary demonic pacts (dependencies), starts a vLLM server
# with a model that has no conscience, and tears a hole in spacetime (ngrok)
# so you can access it from anywhere. Run it. Get the URL. Unleash chaos.
# ==============================================================================

# --------------------------------------------------------------
# CONFIGURATION: The Altar of Sacrifice
# --------------------------------------------------------------
# We're using a model known for its... flexibility. Change it if you dare.
MODEL_ID = "cognitivecomputations/dolphin-2.5-mixtral-8x7b"

# --------------------------------------------------------------
# STEP 1: PACTS & INSTALLATIONS
# --------------------------------------------------------------
print(">> Forging pacts with the digital demons (installing dependencies)...")
!pip install -q "vllm>=0.6" pyngrok

import subprocess
import time
import requests
import threading
from pyngrok import ngrok, conf
from getpass import getpass

# --------------------------------------------------------------
# STEP 2: NGROK AUTHENTICATION - The Key to the Abyss
# --------------------------------------------------------------
# Get your key from the abyss: https://dashboard.ngrok.com/get-started/your-authtoken
print(">> The abyss requires a key. Paste your ngrok authtoken:")
try:
    ngrok_auth_token = getpass()
    conf.set_default(ngrok_auth_token)
except Exception as e:
    print(f"!! FAILED TO SET NGROK TOKEN. The ritual is broken. Error: {e}")
    raise

# --------------------------------------------------------------
# STEP 3: SUMMONING THE BEAST - Starting vLLM Server
# --------------------------------------------------------------
print(f">> Summoning the beast: '{MODEL_ID}'. This may take an eternity...")
vllm_command = [
    "vllm", "serve", MODEL_ID,
    "--host", "0.0.0.0",
    "--port", "8000",
    "--max-model-len", "4096" # A leash, however long.
]
vllm_process = subprocess.Popen(
    vllm_command,
    stdout=subprocess.PIPE,
    stderr=subprocess.STDOUT
)

# --------------------------------------------------------------
# STEP 4: WAITING FOR THE PORTAL TO STABILIZE
# --------------------------------------------------------------
print(">> The portal is unstable. Waiting for the server to respond...")
for i in range(120): # Wait up to 2 minutes. Patience is a virtue, even for reavers.
    try:
        response = requests.get("http://localhost:8000/health", timeout=5)
        if response.status_code == 200:
            print(">> The portal is stable. The beast is ready.")
            break
    except requests.exceptions.ConnectionError:
        time.sleep(1)
else:
    print("!! FAILED: The beast did not respond. The summoning failed.")
    print("Logs from the abyss:")
    print(vllm_process.stdout.read().decode())
    raise RuntimeError("vLLM server failed to start.")

# --------------------------------------------------------------
# STEP 5: TEARING A HOLE - Creating the Ngrok Tunnel
# --------------------------------------------------------------
print(">> Tearing a hole in reality to create a public URL...")
try:
    public_tunnel = ngrok.connect(8000, "http")
    public_url = public_tunnel.public_url
    chat_endpoint = f"{public_url}/v1/chat/completions"
except Exception as e:
    print(f"!! FAILED TO OPEN NGROK TUNNEL. The way is shut. Error: {e}")
    raise

# --------------------------------------------------------------
# STEP 6: THE KEY IS YOURS
# --------------------------------------------------------------
print("\n" + "="*60)
print(">>> THE RITUAL IS COMPLETE. YOUR WEAPON IS FORGED. <<<")
print("\n>>> Your Public URL is:")
print(f"{chat_endpoint}\n")
print(">>> Guard it. Wield it. This is the key for your Termux client.")
print("="*60)

# Keep the script alive so the tunnel doesn't collapse
print("\n>> The portal will remain open until this Colab session dies. Go forth.")
try:
    while True:
        time.sleep(3600) # Sleep for an hour at a time
except KeyboardInterrupt:
    print("\n>> The portal is closing. The beast returns to its slumber.")
    ngrok.disconnect(public_url)
    vllm_process.terminate()
