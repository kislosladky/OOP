package ru.nsu.kislitsyn;


import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class PrimeServer {
    public BlockingDeque<Task> tasks;
    public AtomicBoolean foundNonPrime = new AtomicBoolean(false);
    public AtomicInteger ongoingTasks = new AtomicInteger(0);

    public PrimeServer() {
        tasks = new LinkedBlockingDeque<>();
    }

    private void sendBroadcast(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            byte[] messageBytes = message.getBytes();
            DatagramPacket packet = new DatagramPacket(messageBytes,
                    messageBytes.length,
                    InetAddress.getByName("192.168.0.255"),
                    8081);

            socket.send(packet);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void splitNumbersToTasks(List<Integer> numbers) {
        int taskSize = 50;
        int currentIndex = 0;
        while (numbers.size() >= currentIndex) {
            this.tasks.add(new Task(numbers.subList(currentIndex, Math.min(currentIndex + taskSize, numbers.size()))));
            currentIndex += taskSize;
        }
    }


    public boolean compute(List<Integer> numbers) {
        splitNumbersToTasks(numbers);

        sendBroadcast("conn");

        List<ServerHandler> serverHandlers = new ArrayList<>();
        Socket socket;
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (!tasks.isEmpty() && !foundNonPrime.get()) {
                socket = serverSocket.accept();
                var handler = new ServerHandler(socket, serverSocket, tasks, foundNonPrime, ongoingTasks);
                handler.start();
                serverHandlers.add(handler);
            }
        } catch (IOException exception) {
            //TODO to catch
            System.err.println(exception.getMessage() + " or computing is finished");
        }

        sendBroadcast("stop");

        try {
            for (ServerHandler handler : serverHandlers) {
                handler.join();
            }
        } catch (InterruptedException exception) {
            //TODO to catch
            System.err.println(exception.getMessage());
        }

        return foundNonPrime.get();
    }

    public static void main(String[] args) {
        PrimeServer client = new PrimeServer();
        List<Integer> numbers;
        try {
            numbers = InputReader.getNumbers(Files.newInputStream(Path.of("numbers.txt")));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println(client.compute(numbers));
    }
}
