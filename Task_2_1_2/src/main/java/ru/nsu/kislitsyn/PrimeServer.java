package ru.nsu.kislitsyn;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Class that searches non-prime number in file by sending pieces of the array
 * to clients and then collecting answers from them.
 */
public class PrimeServer {
    Deque<Task> tasks;

    /**
     * A simple constructor.
     */
    public PrimeServer() {
        tasks = new ArrayDeque<>();
    }

    /**
     * Fills the task list by splitting a list inti smaller lists.
     *
     * @param numbers list of numbers that is needed to be split in tasks.
     */
    private void splitNumbersToTasks(List<Integer> numbers) {
        int taskSize = 50;
        int currentIndex = 0;
        while (numbers.size() >= currentIndex) {
            this.tasks.add(new Task(numbers.subList(currentIndex, Math.min(currentIndex + taskSize, numbers.size()))));
            currentIndex += taskSize;
        }
    }


    /**
     * @param serverSocket server socket that we need to accept.
     * @param selector selector where we can add a new socketChannel.
     * @throws IOException if anything goes wrong (probably socket or selector is closed)
     */
    private void addToSelectorAndSendTask(ServerSocketChannel serverSocket, Selector selector)
            throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("Added client");
        sendTask(client);
    }


    /**
     * Picks a new task to send.
     *
     * @return new task that is going to be sent to a new client.
     */
    private Task getTask() {
        Task taskToSend = tasks.poll();
        if (taskToSend == null) {
            return null;
        }

        while(taskToSend.getRemoteAddress() != null
                && !Broadcaster.checkServer(taskToSend.getRemoteAddress())) {
            Task newTask = tasks.poll();
            tasks.addFirst(taskToSend);
            if (newTask == null) {
                return null;
            }
            taskToSend = newTask;
            System.out.println("Changing task to send");
        }

        return taskToSend;
    }


    /**
     * Sends a task to client's socketChannel.
     * Picks the task that is not sent yet, or it's server is dead.
     *
     * @param socketChannel a socket of client that is waiting for the task.
     */
    private void sendTask(SocketChannel socketChannel) {
        Gson gson = new Gson();

        Task taskToSend = getTask();
        if (taskToSend == null) {
            return; //TODO do I need to return or something?
        }

        SocketAddress remoteAddress;
        try {
            remoteAddress = socketChannel.getRemoteAddress();
        } catch (IOException e) {
            tasks.addFirst(taskToSend);
            return;
        }

        taskToSend.setRemoteAddress(remoteAddress);

        tasks.addLast(taskToSend);
        String toSend = gson.toJson(taskToSend.getNumbers()) + "\n";
        try {
            socketChannel.write(ByteBuffer.wrap(toSend.getBytes()));
            System.out.println("task is sent");
        } catch (IOException e) {
            e.printStackTrace();
            //TODO to handle
        }
    }


    /**
     * Reads answer from client.
     *
     * @param channel channel of client's socket.
     * @return true if client found non-prime.
     * @throws IOException if something went wrong.
     */
    private boolean readAnswer(SelectableChannel channel) throws IOException {
        SocketChannel socket = (SocketChannel) channel;
        ByteBuffer buffer = ByteBuffer.allocate(10);

        int readCnt = socket.read(buffer);
        if (readCnt == -1) {
            //TODO catch dead server
            throw new IOException();
        }
        String answer = new String(buffer.array(), StandardCharsets.UTF_8).trim();
        System.out.println(answer);
        return answer.equals("true");
    }

    /**
     * Deletes task from list by checking it's address.
     *
     * @param channel whose task needs to be deleted.
     * @throws IOException if channel is closed.
     */
    private void deleteTaskByRemoteAddress(SelectableChannel channel) throws IOException {
        SocketAddress address = ((SocketChannel)channel).getRemoteAddress();

        tasks.removeIf(x -> x.getRemoteAddress() != null && x.getRemoteAddress().equals(address));
    }

    /**
     * The main function that processes a list of numbers.
     *
     * @param numbers list of numbers we need to check.
     * @return if there is a non-prime number.
     */
    private boolean work(List<Integer> numbers) {
        splitNumbersToTasks(numbers);

        try(Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
        ) {
            serverSocket.bind(new InetSocketAddress(8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server socket is ready");
            Broadcaster.sendBroadcast("conn");
            while(!tasks.isEmpty()) {
                selector.select();
                System.out.println("Selected keys");
                Set<SelectionKey> keys = selector.selectedKeys();
                //TODO кидать таску новым серверам и тем, что закончили предыдущую таску
//                for (SelectionKey key : keys) {
                Iterator<SelectionKey> iter = keys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        addToSelectorAndSendTask(serverSocket, selector);
                    }

                    try {
                        if (key.isReadable()) {
                            if (readAnswer(key.channel())) {
                                Broadcaster.sendBroadcast("stop");
                                return true;
                            }
                            deleteTaskByRemoteAddress(key.channel());
                        }
                    } catch (IOException ignored) {
                        ignored.printStackTrace();
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        Broadcaster.sendBroadcast("stop");

        return false;
    }

    /**
     * Main.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        PrimeServer server = new PrimeServer();

        List<Integer> numbers;
        try {
            numbers = InputReader.getNumbers(Files.newInputStream(Path.of("numbers.txt")));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return;
        }

        System.out.println(server.work(numbers));
    }
}
