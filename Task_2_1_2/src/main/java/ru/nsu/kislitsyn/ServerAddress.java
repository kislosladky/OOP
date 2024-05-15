package ru.nsu.kislitsyn;

import java.net.InetAddress;

public record ServerAddress(InetAddress ip, int port) {
}
