package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Outputs patient data over a TCP connection.
 * This class implements the OutputStrategy interface and sends patient data to a connected client.
 * 
 * Small note:
 * - Only one client can connect to the server at a time.
 */
public class TcpOutputStrategy implements OutputStrategy {

    /**
     * The server socket used to listen for incoming client connections.
     */
    private ServerSocket serverSocket;

    /**
     * The client socket representing the connected client.
     */
    private Socket clientSocket;

    /**
     * The writer used to send data to the connected client.
     */
    private PrintWriter out;

    /**
     * Constructs a TcpOutputStrategy and starts a TCP server on the given port.
     * Then the server listens for a client connection and then initializes the output stream for sending data.
     *
     * @param port The port number on which the TCP server will listen for connections.
     * @throws IOException If an error occurs while starting the server or accepting a client connection.
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends patient data to the connected client over the TCP connection.
     * The data is formatted as a CSV string: {patientId,timestamp,label,data}.
     *
     * @param patientId The ID of the patient whose data is being sent.
     * @param timestamp The time at which the data was generated, in milliseconds since the UNIX epoch.
     * @param label     A label describing the type of data (e.g., "ECG", "Alert").
     * @param data      The actual data to be sent (e.g., measurement values or alert status).
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
