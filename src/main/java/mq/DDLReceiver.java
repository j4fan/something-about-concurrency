package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DDLReceiver {


    private static final String DXL_EXCHANGE_NAME = "my-ddl-exchange";
    private static final String TASK_EXCHANGE_NAME = "my-ttl-exchange";
    private static final String ROUTING_KEY_NAME = "routing-key";
    private static final String TTL_QUEUE_NAME = "ttl-queue";
    private static final String DLX_QUEUE_NAME = "dxl-queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("dev");
        factory.setPassword("dev2");
        factory.setPort(5672);
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(DLX_QUEUE_NAME,false,false,false,null);

        channel.queueBind(DLX_QUEUE_NAME, DXL_EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(DLX_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }
}
