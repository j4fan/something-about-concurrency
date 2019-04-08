package mq;

import com.rabbitmq.client.*;

public class ProducerFanout {
    private static final String TASK_EXCHANGE_NAME = "f-exchange";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("dev");
        factory.setPassword("dev2");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(TASK_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String message = "hello world";
            channel.basicPublish(TASK_EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
