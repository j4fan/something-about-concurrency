package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MqCommon {

    public static Channel createChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MqConsts.HOST);
        factory.setUsername(MqConsts.USERNAME);
        factory.setPassword(MqConsts.PASSWORD);
        factory.setPort(MqConsts.PORT);
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        return channel;
    }
}
