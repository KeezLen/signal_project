package com.cardio_generator;

import com.data_management.DataStorage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length > 0 && args[0].equalsIgnoreCase("DataStorage")) {
                // Run DataStorage main method
                DataStorage.main(new String[]{});
            } else {
                // Default to running HealthDataSimulator
                HealthDataSimulator.main(new String[]{});
            }
        } catch (IOException e) {
            // Print the error message and exit
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}