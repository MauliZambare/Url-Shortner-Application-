# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled classes and libraries into the container
COPY bin /app/bin
COPY lib /app/lib
COPY src/index.html /app/src/index.html

# Copy the start script
COPY start.sh /app/

# Make the start script executable
RUN chmod +x /app/start.sh

# Expose the port the app runs on
EXPOSE 8080

# Run the start.sh script when the container launches
CMD ["./start.sh"]
