package com.easychat.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SocketServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Map<String, Socket> socketMap = new HashMap<>();
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Server started , waiting client connect");
            while (true) {
                Socket socket = serverSocket.accept();
                String ip = socket.getInetAddress().getHostAddress();
                String socketKey = ip + ":" + socket.getPort();
                socketMap.put(socketKey, socket);
                System.out.println("Client connected : " + ip + "; port: " + socket.getPort());

                new Thread(() -> {
                    while (true) {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                            String s = bufferedReader.readLine();
                            System.out.println("Client received : " + s);

                            socketMap.forEach((k, v) -> {
                                try {
                                    OutputStream outputStream = v.getOutputStream();
                                    PrintStream printStream = new PrintStream(outputStream);
                                    printStream.println(socketKey + ":" + s);
                                    printStream.flush();
                                    System.out.println(k);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, "receive Content").start();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
