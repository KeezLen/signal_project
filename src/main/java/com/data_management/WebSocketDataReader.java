package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.io.IOException;

public class WebSocketDataReader implements DataReader {
    private WebSocketClient client;

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        // Not used for real-time
        throw new UnsupportedOperationException("Use start() for real-time data.");
    }

    @Override
    public void start(DataStorage dataStorage) throws IOException {
        URI serverUri = URI.create("ws://localhost:12345"); // Adjust port as needed
        client = new WebSocketClient(serverUri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to WebSocket server.");
            }

            @Override
            public void onMessage(String message) {
                try {
                    // Example message: "1,1714376789050,HeartRate,120.0"
                    String[] parts = message.split(",", 4);
                    if (parts.length == 4) {
                        int patientId = Integer.parseInt(parts[0]);
                        long timestamp = Long.parseLong(parts[1]);
                        String label = parts[2];
                        double value = Double.parseDouble(parts[3]);
                        dataStorage.addPatientData(patientId, value, label, timestamp);
                    } else {
                        System.err.println("Malformed message: " + message);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing message: " + message);
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket error: " + ex.getMessage());
                ex.printStackTrace();
            }
        };
        client.connect();
    }
}
