package ru.nsu.kislitsyn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class of client that receives a list of numbers
 * and checks if it has a non-prime one.
 */
public class PrimeClient {
    public InetAddress serverAddress;
    private PrimeChecker primeChecker;
    private int serverPort;

    /**
     * A thread that receives a list if integers through TCP and checks it.
     */
    class PrimeChecker extends Thread {
        /**
         * @param number number to check.
         * @return true if non-prime.
         */
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

        /**
         * Check a list of integers from json.
         *
         * @param input string with a json of integers we need to check.
         * @return true if has a non-prime one.
         */
        private static boolean checkNumbers(String input) {
            Gson gson = new Gson();
            List<Integer> numbers = gson.fromJson(input, new TypeToken<List<Integer>>() {}.getType());
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

        /**
         * Opens TCP connection, receives a json of integers and sends answer back to the server.
         * (True if there is a non-prime integer).
         */
        @Override
        public void run() {
            try (SocketChannel socket = SocketChannel.open(new InetSocketAddress(serverAddress, serverPort))) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readCnt = socket.read(buffer);
                System.out.println("Counting");
                String input = new String(buffer.array(), 0, readCnt);

                boolean answer = checkNumbers(input);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ignored) {
                }
                socket.write(ByteBuffer.wrap(String.valueOf(answer).getBytes()));
                if (answer) {
                    return;
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return;
            }

            primeChecker = new PrimeChecker();
            primeChecker.start();
        }
    }

    /**
     * Starts TCP thread if not started yet.
     */
    private void startTCP() {
        System.out.println("Starting tcp thread");
        if (primeChecker == null) {
            primeChecker = new PrimeChecker();
            primeChecker.start();
        }
    }

    /**
     * Stops tcp thread if works.
     */
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

    /**
     * Sends echo back to server.
     *
     * @param packet packet I need to echo.
     */
    private void echo(DatagramPacket packet) {
        try (DatagramSocket socket = new DatagramSocket()) {
            packet.setData("echo".getBytes());
            socket.send(packet);
        } catch (IOException ignored) {
        }
    }

    /**
     * Main function of UDP thread that listens to the commands of server.
     */
    public void listen() {
        try (DatagramSocket datagramSocket = new DatagramSocket(8081)) {
            DatagramPacket pack = new DatagramPacket(new byte[4], 4);
            while (true) {
                System.out.println("Waiting for the command");
                datagramSocket.receive(pack);
                String data = new String(pack.getData(), StandardCharsets.UTF_8);
                switch (data) {
                    case "conn" -> {
                        System.out.println("Got message:" + data);
                        serverAddress = pack.getAddress();
                        serverPort = 8080;
                        System.out.println("IP is " + serverAddress);
                        startTCP();
                    }
                    case "stop" -> {
                        stopTCP();
                        return;
                    }
                    case "echo" ->
                        echo(pack);

                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Main.
     */
    public static void main(String[] args) {
        PrimeClient server = new PrimeClient();
        server.listen();
    }
}
