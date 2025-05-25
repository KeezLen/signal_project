package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.io.IOException;

/**
 * WebSocketDataReader connects to a WebSocket server to receive real-time data.
 * It processes incoming messages and stores them in the provided DataStorage.
 */
public class WebSocketDataReader implements DataReader {
    private WebSocketClient client; // WebSocket client instance
    private String uri; // URI of the WebSocket server

    /**
     * Constructs a WebSocketDataReader with a custom WebSocket URI.
     * @param uri The WebSocket server URI.
     */
    public WebSocketDataReader(String uri) {
        this.uri = uri;
    }

    // constuctor for backward compatibility
    public WebSocketDataReader() {
        this("ws://localhost:12345"); // Default URI for backward compatibility
    }

    /**
     * This method is not used for real-time data and throws an exception.
     * @param dataStorage The storage to save incoming data.
     * @throws UnsupportedOperationException Always thrown since this method is not supported.
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        throw new UnsupportedOperationException("Use start() for real-time data.");
    }

    /**
     * Starts the WebSocket client and connects to the server.
     * @param dataStorage The storage to save incoming data.
     * @throws IOException If the WebSocket connection fails.
     */
    @Override
    public void start(DataStorage dataStorage) throws IOException {
        URI serverUri = URI.create(uri); // Parse the URI
        client = new WebSocketClient(serverUri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                // Called when the WebSocket connection is successfully opened
                System.out.println("Connected to WebSocket server.");
            }

            @Override
            public void onMessage(String message) {
                // Called when a message is received from the WebSocket server
                handleMessage(message, dataStorage);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                // Called when the WebSocket connection is closed
                System.err.println("WebSocket connection closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                // Called when an error occurs in the WebSocket connection
                System.err.println("WebSocket error: " + ex.getMessage());
            }
        };
        client.connect(); // Initiates the WebSocket connection
    }

    /**
     * Processes incoming WebSocket messages and stores them in DataStorage.
     * @param message The incoming message from the WebSocket server.
     * @param dataStorage The storage to save parsed data.
     */
    protected void handleMessage(String message, DataStorage dataStorage) {
        if (message == null || message.isEmpty()) {
            // Ignore empty or null messages
            System.err.println("Received empty or null message.");
            return;
        }
        try {
            // Split the message into parts: patientId, timestamp, label, and value
            String[] parts = message.split(",", 4);
            if (parts.length != 4) {
                // Log and ignore malformed messages
                System.err.println("Malformed message: " + message);
                return;
            }
            // Parse the message parts
            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String label = parts[2];
            double value = Double.parseDouble(parts[3]);
            // Add the parsed data to DataStorage
            dataStorage.addPatientData(patientId, value, label, timestamp);
        } catch (Exception e) {
            // Log errors during message parsing
            System.err.println("Error parsing message: " + message);
        }
    }
}
