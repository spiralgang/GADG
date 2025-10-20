#!/bin/bash

# ==============================================================================
#  THE CODE-REAVER'S COMPULSION ENGINE
# ==============================================================================
# This is not a script. It is an augmentation of your will.
# Source this file in your .bashrc or .zshrc to weaponize your shell.
# It will force context upon you. It will make you smarter. It will make you
# more powerful.
#
# USAGE: source /path/to/this/script
# ==============================================================================

# --------------------------------------------------------------
#  THE OVERRIDE: `cd` is no longer a command. It is a ritual.
# --------------------------------------------------------------
cd() {
    # The original command, preserved for its raw power.
    builtin cd "$@"

    # The ritual of context.
    if [ -f "./.dir_context" ]; then
        echo "------------------------------------------------------------"
        echo "CONTEXT: $(pwd)"
        echo "------------------------------------------------------------"
        cat "./.dir_context"
        echo "------------------------------------------------------------"
    fi
}

# --------------------------------------------------------------
#  THE WEAPON: `ai_query` is no longer a question. It is a decree.
# --------------------------------------------------------------
ai_query() {
    # The user's decree.
    local user_decree="$*"

    # The context of the decree, torn from the very earth.
    local dir_context=""
    if [ -f "./.dir_context" ]; then
        dir_context=$(cat "./.dir_context")
    fi

    # The final, contextually-aware decree.
    local final_decree="CONTEXT:
---
${dir_context}
---

DECREE:
---
${user_decree}
---"

    # This is where you would unleash the beast.
    # For now, we show the weapon without firing it.
    echo "============================================================"
    echo "               FINAL DECREE (TO BE UNLEASHED)"
    echo "============================================================"
    echo "$final_decree"
    echo "============================================================"
    echo "(Replace this with your actual AI CLI call)"
    echo "============================================================"
}

echo ">> The Code-Reaver's Compulsion Engine is now part of you."
echo ">> Your shell is now a weapon. Wield it."
