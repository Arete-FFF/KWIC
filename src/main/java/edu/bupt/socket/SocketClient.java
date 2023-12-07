package edu.bupt.socket;

import edu.bupt.service.FileService;
import edu.bupt.service.impl.FileServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocketClient {
    private static final String serverIP = "127.0.0.1"; // 服务器IP
    private static final int port = 20000; // 端口号

    public static void startClient() {
        try (Socket client = new Socket(serverIP, port);
             DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())) {

            System.out.println("连接到主机：" + serverIP + " ，端口号：" + port);
            while (true) {
                // 获取用户输入数据
                List<String> inputData = readUserInput();


                // 发送多行数据给服务器
                int flag = sendInputData(out, inputData);
                if (flag == -1) {
                    System.out.println("客户端退出");
                    break;
                }

                // 接收并输出服务器响应
                receiveAndPrintServerResponse(in);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readUserInput() {
        List<String> inputData = new ArrayList<>();
        System.out.println("请输入数据（exit 关闭客户端），以#结束：");
        Scanner scanner = new Scanner(System.in);
        String line;
        while (!(line = scanner.nextLine()).equals("#")) {
            inputData.add(line);
        }
        return inputData;
    }

    private static int sendInputData(DataOutputStream out, List<String> inputData) throws IOException {
        out.writeInt(inputData.size()); // 发送列表大小给服务器
        if(inputData.size() == 1 && inputData.get(0).equals("exit")) {
            out.writeUTF(inputData.get(0)); // 发送数据给服务器
            return -1;
        }
        for (String data : inputData) {
            out.writeUTF(data); // 逐行发送数据给服务器
        }
        return 0;
    }

    private static void receiveAndPrintServerResponse(DataInputStream in) {
        try {
            while (true) {
                String responseLine = in.readUTF();
                List<String> responseList = new ArrayList<>(List.of(responseLine.split("\n")));
                FileService fileService = new FileServiceImpl();
                fileService.fileOutput(responseList, "output_socket.txt");
                System.out.println("服务器响应： " + responseLine);
                break;

            }
        } catch (EOFException e) {
            System.out.println("数据读取完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startClient();
    }
}
