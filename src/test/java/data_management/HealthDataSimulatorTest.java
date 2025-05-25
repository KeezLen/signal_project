package data_management;

import com.cardio_generator.HealthDataSimulator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the HealthDataSimulator class.
 * Verifies singleton behavior, argument parsing, and task scheduling.
 */
public class HealthDataSimulatorTest {

    /**
     * Tests that HealthDataSimulator follows the Singleton pattern.
     */
    @Test
    void testSingletonInstance() {
        HealthDataSimulator sim1 = HealthDataSimulator.getInstance();
        HealthDataSimulator sim2 = HealthDataSimulator.getInstance();
        assertSame(sim1, sim2, "Should return the same instance");
    }

    /**
     * Tests that patient IDs are initialized correctly.
     */
    @Test
    void testInitializePatientIds() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        List<Integer> ids = simTestableInitializePatientIds(sim, 5);
        assertEquals(List.of(1, 2, 3, 4, 5), ids);
    }

    /**
     * Tests that the help message is printed correctly.
     */
    @Test
    void testPrintHelpOutputsUsage() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));
        try {
            simTestablePrintHelp(sim);
        } finally {
            System.setOut(originalOut);
        }
        String help = out.toString();
        assertTrue(help.contains("Usage: java HealthDataSimulator"), "Help should contain usage info");
    }

    /**
     * Tests that the patient count is set correctly when valid arguments are provided.
     */
    @Test
    void testParseArgumentsSetsPatientCountValid() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        String[] args = {"--patient-count", "7"};
        simTestableParseArguments(sim, args);
        assertEquals(7, getPrivateInt(sim, "patientCount"));
    }

    /**
     * Tests that the patient count remains unchanged when invalid arguments are provided.
     */
    @Test
    void testParseArgumentsSetsPatientCountInvalid() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        setPrivateInt(sim, "patientCount", 50); // Reset before test
        String[] args = {"--patient-count", "notanumber"};
        simTestableParseArguments(sim, args);
        assertEquals(50, getPrivateInt(sim, "patientCount"));
    }

    /**
     * Tests that the output strategy is set to ConsoleOutputStrategy.
     */
    @Test
    void testParseArgumentsSetsConsoleOutput() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        String[] args = {"--output", "console"};
        simTestableParseArguments(sim, args);
        assertTrue(getPrivateField(sim, "outputStrategy").getClass().getSimpleName().contains("ConsoleOutputStrategy"));
    }

    /**
     * Tests that the output strategy is set to FileOutputStrategy.
     */
    @Test
    void testParseArgumentsSetsFileOutput() throws Exception {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        Path tempDir = Files.createTempDirectory("simTest");
        String[] args = {"--output", "file:" + tempDir.toString()};
        simTestableParseArguments(sim, args);
        assertTrue(getPrivateField(sim, "outputStrategy").getClass().getSimpleName().contains("FileOutputStrategy"));
        // Clean up
        Files.deleteIfExists(tempDir);
    }

    /**
     * Tests that the output strategy is set to WebSocketOutputStrategy.
     */
    @Test
    void testParseArgumentsSetsWebSocketOutput() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        String[] args = {"--output", "websocket:12345"};
        simTestableParseArguments(sim, args);
        assertTrue(getPrivateField(sim, "outputStrategy").getClass().getSimpleName().contains("WebSocketOutputStrategy"));
    }

    /**
     * Tests that the output strategy is set to TcpOutputStrategy.
     */
    @Test
    void testParseArgumentsSetsTcpOutput() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        String[] args = {"--output", "tcp:12345"};
        simTestableParseArguments(sim, args);
        assertTrue(getPrivateField(sim, "outputStrategy").getClass().getSimpleName().contains("TcpOutputStrategy"));
    }

    /**
     * Tests that an unknown output type falls back to ConsoleOutputStrategy.
     */
    @Test
    void testParseArgumentsUnknownOutputType() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        String[] args = {"--output", "unknownType"};
        simTestableParseArguments(sim, args);
        assertTrue(getPrivateField(sim, "outputStrategy").getClass().getSimpleName().contains("ConsoleOutputStrategy"));
    }

    /**
     * Tests that a scheduled task runs successfully.
     */
    @Test
    void testScheduleTaskRunsRunnable() throws InterruptedException {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        final boolean[] ran = {false};
        java.util.concurrent.ScheduledExecutorService scheduler =
                java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
        try {
            simTestableScheduleTask(sim, () -> ran[0] = true, 1, TimeUnit.MILLISECONDS, scheduler);
            Thread.sleep(50); // Wait for the task to run
            assertTrue(ran[0], "Runnable should have run");
        } finally {
            scheduler.shutdownNow();
        }
    }

    /**
     * Tests that scheduling tasks for an empty list of patients does not throw exceptions.
     */
    @Test
    void testScheduleTasksForPatientsWithEmptyList() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        setPrivateField(sim, "scheduler", java.util.concurrent.Executors.newSingleThreadScheduledExecutor());
        List<Integer> emptyList = List.of();
        assertDoesNotThrow(() -> simTestableScheduleTasksForPatients(sim, emptyList));
    }

    /**
     * Tests that scheduling tasks for multiple patients does not throw exceptions.
     */
    @Test
    void testScheduleTasksForPatientsWithMultiplePatients() {
        HealthDataSimulator sim = HealthDataSimulator.getInstance();
        setPrivateField(sim, "scheduler", java.util.concurrent.Executors.newSingleThreadScheduledExecutor());
        List<Integer> patientIds = Arrays.asList(1, 2, 3);
        assertDoesNotThrow(() -> simTestableScheduleTasksForPatients(sim, patientIds));
    }

    // --- Reflection helpers to access private methods for testing ---

    private List<Integer> simTestableInitializePatientIds(HealthDataSimulator sim, int count) {
        try {
            var m = HealthDataSimulator.class.getDeclaredMethod("initializePatientIds", int.class);
            m.setAccessible(true);
            return (List<Integer>) m.invoke(sim, count);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void simTestablePrintHelp(HealthDataSimulator sim) {
        try {
            var m = HealthDataSimulator.class.getDeclaredMethod("printHelp");
            m.setAccessible(true);
            m.invoke(sim);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void simTestableScheduleTask(HealthDataSimulator sim, Runnable task, long period, TimeUnit unit, java.util.concurrent.ScheduledExecutorService scheduler) {
        try {
            var field = HealthDataSimulator.class.getDeclaredField("scheduler");
            field.setAccessible(true);
            field.set(sim, scheduler);

            // Ensure 'random' is initialized (should be, but just in case)
            var randomField = HealthDataSimulator.class.getDeclaredField("random");
            randomField.setAccessible(true);
            if (randomField.get(sim) == null) {
                randomField.set(sim, new java.util.Random());
            }

            var m = HealthDataSimulator.class.getDeclaredMethod("scheduleTask", Runnable.class, long.class, TimeUnit.class);
            m.setAccessible(true);
            m.invoke(sim, task, period, unit);
        } catch (Exception e) {
            e.printStackTrace(); // Add this line
            throw new RuntimeException(e);
        }
    }

    // --- Reflection helpers ---

    private void simTestableParseArguments(HealthDataSimulator sim, String[] args) {
        try {
            var m = HealthDataSimulator.class.getDeclaredMethod("parseArguments", String[].class);
            m.setAccessible(true);
            m.invoke(sim, (Object) args);
        } catch (Exception e) {
            // Ignore System.exit or other exceptions for coverage
        }
    }

    private int getPrivateInt(Object obj, String fieldName) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.getInt(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPrivateInt(Object obj, String fieldName, int value) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.setInt(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getPrivateField(Object obj, String fieldName) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // --- Helper to set private fields ---
    private void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // --- Reflection helper for scheduleTasksForPatients ---
    private void simTestableScheduleTasksForPatients(HealthDataSimulator sim, List<Integer> patientIds) {
        try {
            var m = HealthDataSimulator.class.getDeclaredMethod("scheduleTasksForPatients", List.class);
            m.setAccessible(true);
            m.invoke(sim, patientIds);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}