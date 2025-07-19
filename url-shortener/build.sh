#!/bin/bash

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile the Compiler.java first
javac -d bin src/Compiler.java

# Run the Compiler.java to compile the rest of the project
java -cp bin Compiler

if [ $? -eq 0 ]; then
    echo "Build successful!"
else
    echo "Build failed!"
    exit 1
fi
