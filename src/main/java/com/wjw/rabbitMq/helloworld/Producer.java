package com.wjw.rabbitMq.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		// 创建连接工厂
		ConnectionFactory connFactory = new ConnectionFactory();
		// 设置rabbitMQ
		connFactory.setHost("localhost");
		// 创建连接
		Connection connection = connFactory.newConnection();
		// 创建频道
		Channel channel = connection.createChannel();
		// 队列声明
		// Parameters:
		// queue --the name of the queue 队列名称
		// durable --true if we are declaring a durable queue (the queue will
		// survive a server restart)，是否创建一个持久化的队列，重启后依然存在
		// exclusive true if we are declaring an exclusive queue (restricted to
		// this connection)，创建一个独立的队列,排外的队列：设置了排外为true的队列只可以在本次的连接中被访问
		// autoDelete true if we are declaring an autodelete queue (server will
		// delete it when no longer in use)，当队列不再使用时，是否删除
		// arguments other properties (construction arguments) for the queue
		channel.queueDeclare("HelloWorld", true, false, true, null);
		//消息内容
		
		int i = 0;
		while(i<=1000){
			String msg = "雷猴啊，朋友！"+i;
			channel.basicPublish("", "HelloWorld", null, msg.getBytes("UTF-8"));
			Thread.sleep(2000);
			System.out.println("消息发送，"+i);
			i++;
		}
		channel.close();
		connection.close();
	}
}
