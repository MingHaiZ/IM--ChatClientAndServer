package com.easychat.test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8888);
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            new Thread(() -> {
                while (true) {
                    System.out.println("请输入内容: ");
                    Scanner scanner = new Scanner(System.in);
                    String s = scanner.nextLine();
                    printStream.println(s);
                    printStream.flush();
                }
            }, "Send Content").start();

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            new Thread(() -> {
                try {
                    while (true) {
                        String s = bufferedReader.readLine();
                        System.out.println("收到服务端返回内容: " + s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
