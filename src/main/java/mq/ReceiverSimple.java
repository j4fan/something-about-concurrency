package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static mq.MqConsts.DIRECT_QUEUE_NAME;

public class ReceiverSimple {


    public static void main(String[] args) throws IOException, TimeoutException {
        final Channel channel = MqCommon.createChannel();

        channel.queueDeclare(DIRECT_QUEUE_NAME, true, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                System.out.println("do work of message start..." + message);
                Thread.sleep(100000);
                System.out.println("do work of mesage end ..." + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(DIRECT_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }

}
