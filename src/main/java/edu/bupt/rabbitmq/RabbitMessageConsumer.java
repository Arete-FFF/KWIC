package edu.bupt.rabbitmq;

import com.rabbitmq.client.*;
import edu.bupt.service.FileService;
import edu.bupt.service.KwicService;
import edu.bupt.service.impl.FileServiceImpl;
import edu.bupt.service.impl.KwicServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMessageConsumer extends Thread {

    Channel channel;
    String queueName;


    public RabbitMessageConsumer(String queueName) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置RabbitMQ地址
        factory.setHost("39.105.117.116");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        //创建一个新的连接
        Connection connection = factory.newConnection();

        //创建一个通道
        channel = connection.createChannel();
        this.queueName = queueName;

        //声明要关注的队列
        channel.queueDeclare(this.queueName, false, false, false, null);
        System.out.println("Customer Waiting Received messages");
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new RabbitMessageConsumer("KWIC").start();
    }

    @Override
    public void run() {
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        List<String> resultList = new ArrayList<>();
        FileService fileService = new FileServiceImpl();
        KwicService kwicService = new KwicServiceImpl();
        ScheduledExecutorService[] executorService = {Executors.newSingleThreadScheduledExecutor()};
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                resultList.add(new String(body, "UTF-8"));
                System.out.println("Customer Received '" + resultList.get(resultList.size()-1) + "'");

                // 取消上一个定时任务（如果存在）
                executorService[0].shutdownNow();

                // 使用 ScheduledExecutorService 延迟执行 kwic 与 fileOutput 操作
                executorService[0] = Executors.newSingleThreadScheduledExecutor();
                executorService[0].schedule(() -> {
                    kwicService.kwic(resultList);
                    fileService.fileOutput(resultList, "output_rabbit.txt");
                }, 1, TimeUnit.SECONDS);
            }
        };

        try {
            channel.basicConsume(this.queueName, true, consumer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
