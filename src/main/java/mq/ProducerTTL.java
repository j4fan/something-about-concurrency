package mq;

import com.rabbitmq.client.*;

public class ProducerTTL {

    private static final String TASK_EXCHANGE_NAME = "my-ttl-exchange";

    private static final String ROUTING_KEY_NAME = "routing-key";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("dev");
        factory.setPassword("dev2");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(TASK_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String message  = "Hello, world!";
            byte[] messageBodyBytes = "Hello, world!".getBytes();
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration("10000")
                    .build();
            channel.basicPublish(TASK_EXCHANGE_NAME, ROUTING_KEY_NAME, properties, messageBodyBytes);

            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
