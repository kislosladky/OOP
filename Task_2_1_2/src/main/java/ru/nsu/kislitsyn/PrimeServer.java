package ru.nsu.kislitsyn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class PrimeServer {
    public InetAddress clientAddress;
    private PrimeChecker primeChecker;
    private int clientPort;

    class PrimeChecker extends Thread {
        private static boolean isNonPrime(Integer number) {
            if (number % 2 == 0) {
                return false;
            }

            int border = (int)Math.sqrt(number) + 1;

            for (int i = 3; i <= border; i += 2) {
                if (number % i == 0) {
                    return true;
                }
            }
            return false;
        }

        private static boolean checkNumbers(String input) {
            Gson gson = new Gson();
            Task task = gson.fromJson(input, new TypeToken<Task>(){}.getType());
            List<Integer> numbers = task.numbers();
            for (Integer number : numbers) {
                if (isNonPrime(number)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void run() {
            String input = null;

            try (Socket socket = new Socket(clientAddress, clientPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                input = in.readLine();
                boolean answer = checkNumbers(input);
                out.println(answer);
            } catch (IOException e) {
                //TODO to handle
                System.err.println(e.getMessage());
            }
        }
    }

    private void startTCP() {
        primeChecker = new PrimeChecker();
        primeChecker.start();
    }

    private void stopTCP() {
        if (primeChecker != null) {
            primeChecker.interrupt();
        }
    }


    //TODO подумать, как останавливаться, когда другой сервер нашел составное число
    public static void main(String[] args) {
        PrimeServer server = new PrimeServer();
        try (DatagramSocket ds = new DatagramSocket(8081)) {
            DatagramPacket pack = new DatagramPacket(new byte[4], 4);
            while (true) {
                ds.receive(pack);
                String data = new String(pack.getData(), StandardCharsets.UTF_8);
                System.out.println("Data is " + data);
                switch (data) {
                    case "conn" -> {
                        server.clientAddress = pack.getAddress();
                        server.clientPort = 8080;
                        System.out.println("Connecting to " + server.clientAddress.toString() + " on port " + server.clientPort);
                        server.startTCP();

                    }
                    case "stop" -> {server.stopTCP(); return;}
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
