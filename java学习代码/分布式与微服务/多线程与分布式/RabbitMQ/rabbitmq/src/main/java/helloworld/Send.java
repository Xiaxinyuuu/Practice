package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Hello World 发送类，连接到RabbitMQ的服务端，然后发送一条消息，然后推出
 */
public class Send {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("124.223.184.27");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //建立连接
        Connection connection = factory.newConnection();
        //获得信道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发布消息
        String message = "Hello World";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("utf-8"));
        System.out.println("发送了消息" + message);
        //关闭连接
        channel.close();
        connection.close();
    }
}
