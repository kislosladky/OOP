package ru.nsu.kislitsyn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.SocketAddress;
import java.util.List;

/**
 * Kinda like record for task that consists of
 * small list of integers and SocketAddress of
 * client that checks this task.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Task {
    private final List<Integer> numbers;
    private SocketAddress remoteAddress = null;
}