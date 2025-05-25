package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.io.IOException;

public class WebSocketDataReader implements DataReader {
    private WebSocketClient client;
    private String uri; 

    public WebSocketDataReader(String uri) {
        this.uri = uri;
    }

    public WebSocketDataReader() {
        this("ws://localhost:12345"); // Default URI for backward compatibility
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        // Not used for real-time
        throw new UnsupportedOperationException("Use start() for real-time data.");
    }

    @Override
    public void start(DataStorage dataStorage) throws IOException {
        URI serverUri = URI.create(uri); // Use the instance URI
        client = new WebSocketClient(serverUri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to WebSocket server.");
            }

            @Override
            public void onMessage(String message) {
                handleMessage(message, dataStorage);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.err.println("WebSocket connection closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket error: " + ex.getMessage());
            }
        };
        client.connect();
    }

    protected void handleMessage(String message, DataStorage dataStorage) {
        if (message == null || message.isEmpty()) {
            System.err.println("Received empty or null message.");
            return;
        }
        try {
            String[] parts = message.split(",", 4);
            if (parts.length != 4) {
                System.err.println("Malformed message: " + message);
                return;
            }
            int patientId = Integer.parseInt(parts[0]);
            long timestamp = Long.parseLong(parts[1]);
            String label = parts[2];
            double value = Double.parseDouble(parts[3]);
            dataStorage.addPatientData(patientId, value, label, timestamp);
        } catch (Exception e) {
            System.err.println("Error parsing message: " + message);
        }
    }
}
