package edu.bupt;

import edu.bupt.rabbitmq.RabbitMessageConsumer;
import edu.bupt.rabbitmq.RabbitMessageProducer;
import edu.bupt.service.FileService;
import edu.bupt.service.KwicService;
import edu.bupt.service.StringService;
import edu.bupt.service.impl.FileServiceImpl;
import edu.bupt.service.impl.KwicServiceImpl;
import edu.bupt.service.impl.StringServiceImpl;
import edu.bupt.socket.SocketServer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        List<String> inputList;
        List<String> ResultList;
        Scanner scanner = new Scanner(System.in);
        KwicService kwicService = new KwicServiceImpl();
        FileService fileService = new FileServiceImpl();

        System.out.print("请输入对应数字 1:文件输入 2:Socket输入 3:RabbitMq输入 4:数据库输入\n");
        int input = scanner.nextInt();

        switch (input) {
            case 1:
                // 文件输入方式
                inputList = fileService.fileInput();
                ResultList = kwicService.kwic(inputList);
                fileService.fileOutput(ResultList,"output_文件输入.txt");
                break;
            case 2:
                // Socket输入方式
                System.out.println("请另开启客户端，客户端文件为edu/bupt/socket/SocketClient.java");
                SocketServer.startServer();
                break;
            case 3:
                // RabbitMq输入方式
                inputList = fileService.fileInput();
                new RabbitMessageProducer("KWIC", inputList).start();
                new RabbitMessageConsumer("KWIC").start();
                break;
            case 4:
                // 数据库输入方式
                StringService stringService = new StringServiceImpl();
                inputList = stringService.getStringList();
                for (String item : inputList) {
                    System.out.println(item);
                }
                ResultList = kwicService.kwic(inputList);

                stringService.deleteOutput();
                for (String item : ResultList) stringService.addOutputString(item);
                System.out.println("数据已写入数据库中");
                break;
            default:
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                break;
        }
    }
}
