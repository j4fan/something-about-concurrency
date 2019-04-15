package mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static mq.MqConsts.FANOUT_EXCHANGE_NAME;

public class ProducerFanout {


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqCommon.createChannel();
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String message = "hello world";
        channel.basicPublish(FANOUT_EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
