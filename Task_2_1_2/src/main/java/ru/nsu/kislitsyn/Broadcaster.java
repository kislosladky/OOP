package ru.nsu.kislitsyn;

import java.io.IOException;
import java.net.*;

/**
 * Class with some functions that send UDP packets.
 * Used in PrimeServer.
 */
public class Broadcaster {
    /**
     * Checks if server is alive by sending him a message and waiting for echo.
     *
     * @param remoteAddress address that I need to check.
     * @return true if server answered to the message.
     */
    public static boolean checkServer(SocketAddress remoteAddress) {
        InetSocketAddress socketAddress = (InetSocketAddress) remoteAddress;
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] messageBytes = "echo".getBytes();
            DatagramPacket packet = new DatagramPacket(messageBytes,
                    messageBytes.length,
                    socketAddress.getAddress(),
                    8081);

            socket.send(packet);
            socket.setSoTimeout(1000);
            socket.receive(packet);
            return new String(packet.getData()).equals("echo");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Sends a message to clients using UDP broadcast.
     *
     * @param message a message to send to the clients.
     */
    public static void sendBroadcast(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            byte[] messageBytes = message.getBytes();
            DatagramPacket packet = new DatagramPacket(messageBytes,
                    messageBytes.length,
                    InetAddress.getByName("192.168.0.255"),
                    8081);

            socket.send(packet);
            System.out.println(message + " is sent");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
