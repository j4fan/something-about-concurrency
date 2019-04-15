package mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ReceiverTTL {


    private static final String DDL_EXCHANGE_NAME = "my-ddl-exchange";
    private static final String TASK_EXCHANGE_NAME = "my-ttl-exchange";
    private static final String ROUTING_KEY_NAME = "routing-key";
    private static final String TTL_QUEUE_NAME = "ttl-queue";
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("dev");
        factory.setPassword("dev2");
        factory.setPort(5672);
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        channel.exchangeDeclare(DDL_EXCHANGE_NAME, "direct");

        channel.exchangeDeclare(TASK_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("x-dead-letter-exchange", DDL_EXCHANGE_NAME);
        channel.queueDeclare(TTL_QUEUE_NAME,true,false,false,param);
        channel.queueBind(TTL_QUEUE_NAME,TASK_EXCHANGE_NAME,ROUTING_KEY_NAME);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(TTL_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }
}
