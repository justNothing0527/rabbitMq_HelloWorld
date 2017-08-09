package com.wjw.rabbitMq.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @author Administrator
 * 消息接收者
 * HelloWorld
 */
public class Customer {
	public static void main(String[] args) throws IOException, TimeoutException{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		Connection conn = connectionFactory.newConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare("HelloWorld", true, false, true, null);
		 System.out.println("C [*] Waiting for messages. To exit press CTRL+C");
		 
		 Consumer consumer = new DefaultConsumer(channel){
			 @Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				super.handleDelivery(consumerTag, envelope, properties, body);
				String message = new String(body, "UTF-8");  
                System.out.println("C [x] Received '" + message + "'");
			}
		 };
		 //自动回复队列应答
		 channel.basicConsume("HelloWorld", true, consumer);
	}
}
