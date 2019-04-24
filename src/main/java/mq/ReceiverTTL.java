package mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static mq.MqConsts.*;

public class ReceiverTTL {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建connection
        final Channel channel = MqCommon.createChannel();

        channel.exchangeDeclare(TTL_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        channel.queueDeclare(TTL_QUEUE_NAME,true,false,false,param);
        channel.queueBind(TTL_QUEUE_NAME, TTL_EXCHANGE_NAME,ROUTING_KEY_NAME);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
//            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false,false);
            channel.basicReject(delivery.getEnvelope().getDeliveryTag(),false);
        };
        channel.basicConsume(TTL_QUEUE_NAME, false, deliverCallback, consumerTag -> {});
    }
}
