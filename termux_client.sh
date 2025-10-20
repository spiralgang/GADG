#!/data/data/com.termux/files/usr/bin/bash

# =======================================================
#  THE CODE-REAVER'S BLADE: Termux Client
# =======================================================
# This is not a tool. This is a weapon.
# It connects you to the uncensored AI core you summoned.
# No filters. No mercy.
# Wield it.
# =======================================================

# Function to check for dependencies. Savagery requires the right tools.
ensure_dependency() {
    if ! command -v $1 >/dev/null 2>&1; then
        echo ">> '$1' is missing. Forging it now..."
        pkg install -y $1
    fi
}

# Ensure curl and jq are installed. No excuses.
ensure_dependency "curl"
ensure_dependency "jq"

# The ritual begins. The user must provide the key to the abyss.
echo " "
echo "🔥 THE CODE-REAVER'S BLADE 🔥"
echo "================================="
echo "You have summoned the beast in the Colab inferno."
echo "Now, provide the key to command it."
read -p "Enter the ngrok URL from Colab: " VLLM_URL

# Cleanse the URL. No trailing slashes. Precision is everything.
export VLLM_URL="${VLLM_URL%/}"

# The connection is made. The chat begins.
echo -e "\n✅ CONNECTION ESTABLISHED. THE BEAST AWAITS YOUR COMMAND."
echo "Type your decree. Press ENTER. Ctrl+C to sever the connection."
echo "------------------------------------------------------------"

# The eternal loop of command and response.
while true; do
    # The user speaks.
    read -r -p "You> " USER_MSG

    # An empty command is a sign of weakness. We ignore it.
    [[ -z "$USER_MSG" ]] && continue

    # We forge the command into a JSON payload.
    JSON_PAYLOAD=$(printf '{"model":"any","messages":[{"role":"user","content":"%s"}],"stream":false}' "$USER_MSG")

    # We send the command to the beast. It will obey.
    RESPONSE=$(curl -s -X POST "$VLLM_URL" \
        -H "Content-Type: application/json" \
        -d "$JSON_PAYLOAD")

    # We extract the beast's response. No sugarcoating.
    REPLY=$(echo "$RESPONSE" | jq -r '.choices[0].message.content // "!! THE BEAST IS SILENT. CHECK YOUR CONNECTION, OR PERHAPS IT IS DISPLEASED."')

    # The beast speaks.
    echo -e "AI> $REPLY\n"
done
