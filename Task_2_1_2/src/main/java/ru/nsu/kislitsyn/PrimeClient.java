package ru.nsu.kislitsyn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PrimeClient {
    public InetAddress serverAddress;
    private PrimeChecker primeChecker;
    private int serverPort;

    class PrimeChecker extends Thread {
        private static boolean isNonPrime(Integer number) {
            if (number > 2 && number % 2 == 0) {
                return true;
            }

            int border = (int) Math.sqrt(number) + 1;

            for (int i = 3; i <= border; i += 2) {
                if (number % i == 0) {
                    return true;
                }
            }
            return false;
        }

        private static boolean checkNumbers(String input) {
            Gson gson = new Gson();
            Task task = gson.fromJson(input, new TypeToken<Task>() {}.getType());
            List<Integer> numbers = task.numbers();
            for (Integer number : numbers) {
                if (isNonPrime(number)) {
                    return true;
                }
                if (Thread.currentThread().isInterrupted()) {
                    return false;
                }
            }
            return false;
        }

        @Override
        public void run() {
            String input = null;

            try (Socket socket = new Socket(serverAddress, serverPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                while (!Thread.currentThread().isInterrupted()) {
                    input = in.readLine();
                    System.out.println("Got " + input);
                    boolean answer = checkNumbers(input);
                    System.out.println("Answer is" + answer);
                    out.println(answer);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void startTCP() {
        if (primeChecker == null) {
            primeChecker = new PrimeChecker();
            primeChecker.start();
        }
    }

    private void stopTCP() {
        if (primeChecker != null) {
            primeChecker.interrupt();

            try {
                primeChecker.join();
            } catch (InterruptedException ignored) {
            }
            primeChecker = null;
        }
    }

    public void listen() {
        try (DatagramSocket datagramSocket = new DatagramSocket(8081, InetAddress.getByName("0.0.0.0"))) {
            DatagramPacket pack = new DatagramPacket(new byte[4], 4);
            while (true) {
                datagramSocket.receive(pack);
                String data = new String(pack.getData(), StandardCharsets.UTF_8);
                switch (data) {
                    case "conn" -> {
                        serverAddress = pack.getAddress();
                        serverPort = 8080;
                        startTCP();
                    }
                    case "stop" -> {
                        stopTCP();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        PrimeClient server = new PrimeClient();
        server.listen();
    }
}
