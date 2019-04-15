package mq;

public class MqConsts {
    public static final String HOST = "localhost";
    public static final String USERNAME = "dev";
    public static final String PASSWORD = "dev2";
    public static final Integer PORT = 5672;

    public static final String DIRECT_QUEUE_NAME = "d-queue";
    public static final String FANOUT_EXCHANGE_NAME = "f-exchange";


    public static final String TTL_EXCHANGE_NAME = "my-ttl-exchange";
    public static final String TTL_QUEUE_NAME = "ttl-queue";
    public static final String ROUTING_KEY_NAME = "my-ttl-routing-key";
    public static final String DLX_EXCHANGE_NAME = "my-dlx-exchange";
    public static final String DLX_QUEUE_NAME = "dlx-queue";
}
