package ru.nsu.kislitsyn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class ServerHandler extends Thread {
    private final Socket socket;
    private ServerSocket serverSocket;
    private final BlockingDeque<Task> tasks;
    private final AtomicBoolean found;
    private final AtomicInteger ongoingTasks;

    @Override
    public void run() {
        Gson gson = new Gson();
        ongoingTasks.incrementAndGet();
        Task task = tasks.poll();
        if (task == null) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            }
            ongoingTasks.decrementAndGet();
            return;
        }

        String toSend = gson.toJson(task, new TypeToken<Task>(){}.getType());
        try (var out = new PrintWriter(socket.getOutputStream(), true);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ){
            out.println(toSend);
            String answer = in.readLine();
            boolean foundNonPrime = gson.fromJson(answer, Boolean.class);
            System.out.println("result is " + foundNonPrime);
            if (foundNonPrime) {
                found.set(true);
                System.out.println("Found non prime number");
            }
            if (foundNonPrime || (tasks.isEmpty() && ongoingTasks.get() > 0)) {
                serverSocket.close();
            }

        } catch (IOException e) {
            tasks.add(task);
            ongoingTasks.decrementAndGet();
        }
    }
}
