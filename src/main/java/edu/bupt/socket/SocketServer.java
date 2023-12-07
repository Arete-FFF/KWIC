package edu.bupt.socket;

import edu.bupt.service.KwicService;
import edu.bupt.service.impl.KwicServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 该类为多线程类，用于服务端
 * 实现方法
 * 与一客户一线程服务器一样，线程池服务器首先创建一个 ServerSocket 实例。
 * 然后，它创建一个固定大小的线程池，其中每个线程都会调用 connectClient() 方法。
 * connectClient() 方法会一直运行，直到客户端关闭连接。
 */
public class SocketServer {
    private static final int THREADPOOLSIZE = 4; // 线程池大小

    public static void startServer() {
        try (ServerSocket server = new ServerSocket(20000)) {
            System.out.println("服务器启动成功，等待客户端连接...");
            ExecutorService executorService = Executors.newFixedThreadPool(THREADPOOLSIZE);

            for (int i = 0; i < THREADPOOLSIZE; i++) {
                executorService.submit(() -> {
                    try {
                        connectClient(server);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // 等待所有线程执行完毕
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void connectClient(ServerSocket server) throws IOException {
        label:
        while (true) {
            try (Socket client = server.accept()){
                 DataOutputStream out = new DataOutputStream(client.getOutputStream());
                 DataInputStream in = new DataInputStream(client.getInputStream());

                System.out.println("与客户端连接成功！");
                System.out.println("客户端的IP地址是：" + client.getInetAddress().getHostAddress());

                boolean flag = true;
                StringBuilder builder = new StringBuilder();
                builder.append("连接地址: ").append(client.getLocalSocketAddress()).append("\n").append("运行结果：\n");
                while (flag) {
                    int linesCount = in.readInt();
                    List<String> strLines = new ArrayList<>();
                    for (int i = 0; i < linesCount; i++) {
                        String line = in.readUTF();
                        if (line.equals("exit")) {
                            flag = false;
                            System.out.println("客户端 "+client.getInetAddress().getHostAddress()+" 退出");
                            in.close();
                            out.close();
                            client.close();
                            break label;
                        }
                        strLines.add(line);
                        System.out.println(line);
                    }

                    KwicService kwicService = new KwicServiceImpl();
                    List<String> res = new ArrayList<>(kwicService.kwic(strLines));

                    for (String s : res) {
                        builder.append(s).append("\n");
                    }
                    out.writeUTF(builder.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
