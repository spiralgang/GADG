#!/bin/bash

# This script compiles the SMC state machine definitions into the target languages.

# Exit on any error
set -e

# Ensure the output directory exists
mkdir -p src

# Compile the ReactNativeBot state machine for C++
echo "Compiling ReactNativeBot.sm for C++..."
java -jar smc_7_6_0/bin/Smc.jar -c++ -d src smc/ReactNativeBot.sm

# Compile the KotlinNativeBot state machine for Java (for use with Kotlin)
echo "Compiling KotlinNativeBot.sm for Java..."
java -jar smc_7_6_0/bin/Smc.jar -java -d src smc/KotlinNativeBot.sm

echo "SMC compilation finished."
chmod +x workflows/compile_smc.sh