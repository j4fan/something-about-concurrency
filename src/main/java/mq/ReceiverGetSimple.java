package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static mq.MqConsts.DIRECT_QUEUE_NAME;

public class ReceiverGetSimple {


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        final Channel channel = MqCommon.createChannel();
        channel.queueDeclare(DIRECT_QUEUE_NAME, true, false, false, null);
        while (true) {
            GetResponse resp = channel.basicGet(DIRECT_QUEUE_NAME, true);
            if (resp == null) {
                System.out.println("Get Nothing!");
                TimeUnit.MILLISECONDS.sleep(1000);
            } else {
                String message = new String(resp.getBody(), "UTF-8");
                System.out.println("[*] Received " + message);
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        }

    }

}
