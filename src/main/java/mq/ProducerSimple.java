package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import jdk.nashorn.internal.runtime.logging.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static mq.MqConsts.DIRECT_QUEUE_NAME;

@Logger
public class ProducerSimple {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqCommon.createChannel();
        channel.queueDeclare(DIRECT_QUEUE_NAME, true, false, false, null);
        String message = "hello world";
        channel.basicPublish("", DIRECT_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
