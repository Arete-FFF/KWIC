package edu.bupt.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import edu.bupt.service.FileService;
import edu.bupt.service.impl.FileServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class RabbitMessageProducer extends Thread {
    Channel channel;
    String queueName;
    List<String> inputList;


    public RabbitMessageProducer(String queueName, List<String> strings) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置RabbitMQ相关信息
        factory.setHost("39.105.117.116");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        //创建一个新的连接
        Connection connection = factory.newConnection();

        //创建一个通道
        channel = connection.createChannel();
        this.queueName = queueName;
        this.inputList = strings;

        // 声明一个队列
        channel.queueDeclare(this.queueName, false, false, false, null);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        FileService fileService = new FileServiceImpl();
        new RabbitMessageProducer("KWIC", fileService.fileInput()).start();
    }

    @Override
    public void run() {
        //发送消息到队列中
        for(String item : inputList) {
            try {
                channel.basicPublish("", this.queueName, null, item.getBytes("UTF-8"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Producer Send +'" + item + "'");
        }


    }
}