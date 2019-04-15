package mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static mq.MqConsts.DLX_EXCHANGE_NAME;
import static mq.MqConsts.DLX_QUEUE_NAME;
import static mq.MqConsts.ROUTING_KEY_NAME;

public class ReceiverDLX {

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = MqCommon.createChannel();
        channel.exchangeDeclare(DLX_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DLX_QUEUE_NAME, false, false, false, null);
        channel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, ROUTING_KEY_NAME);
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
