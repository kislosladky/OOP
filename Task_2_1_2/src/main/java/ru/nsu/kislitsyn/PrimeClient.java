package ru.nsu.kislitsyn;


import lombok.AllArgsConstructor;

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


public class PrimeClient {
    public BlockingDeque<Task> tasks;
    public AtomicBoolean foundNonPrime = new AtomicBoolean(false);
    public AtomicInteger ongoingTasks = new AtomicInteger(0);

    public PrimeClient() {
        tasks = new LinkedBlockingDeque<>();
    }

    @AllArgsConstructor
    private static class ServerBroadcaster extends Thread {
        String message;

        @Override
        public void run() {
            sendBroadcast();
        }

        private void sendBroadcast() {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setBroadcast(true);
                byte[] message = this.message.getBytes();
                DatagramPacket packet = new DatagramPacket(message,
                        message.length,
                        InetAddress.getByName("255.255.255.255"),
                        8081);

                socket.send(packet);
                System.out.println("\n" + this.message + " is sent");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
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


    private boolean sendTasks() {
        List<ServerHandler> serverHandlers = new ArrayList<>();
        Socket socket;
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (!tasks.isEmpty() && !foundNonPrime.get()) {
                System.out.print("Hell");
                socket = serverSocket.accept();
                var handler = new ServerHandler(socket, serverSocket, tasks, foundNonPrime, ongoingTasks);
                handler.start();
                serverHandlers.add(handler);
                System.out.println("o");
            }
        } catch (IOException exception) {
            //TODO to catch
            System.err.println(exception.getMessage() + " or computing is finished");
        }
        ServerBroadcaster stopper = new ServerBroadcaster("stop");
//        if (foundNonPrime.get() || ) {
        stopper.start();
//        }
        try {
            for (ServerHandler handler : serverHandlers) {
                System.out.println("Joining");
                handler.join();
                System.out.println("Joined");
            }
            stopper.join();
        } catch (InterruptedException exception) {
            //TODO to catch
            System.err.println(exception.getMessage());
        }

        return foundNonPrime.get();
    }

    public static void main(String[] args) {
        PrimeClient client = new PrimeClient();
        ServerBroadcaster finder = new ServerBroadcaster("conn");
        List<Integer> numbers;
        try {
             numbers = InputReader.getNumbers(Files.newInputStream(Path.of("numbers.txt")));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        client.splitNumbersToTasks(numbers);

        finder.setDaemon(true);
        finder.start();

//         и кидаем их серверам json'ами,
        System.out.println(client.sendTasks());
    }
}
