package mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static mq.MqConsts.ROUTING_KEY_NAME;
import static mq.MqConsts.TTL_EXCHANGE_NAME;

public class ProducerTTL {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqCommon.createChannel();
        String message = "Hello, world!";
        byte[] messageBodyBytes = "Hello, world!".getBytes();
        //发送延迟消息，延迟10m后，超时后发往dlx
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .expiration("10000")
                .build();
        channel.basicPublish(TTL_EXCHANGE_NAME, ROUTING_KEY_NAME, properties, messageBodyBytes);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
