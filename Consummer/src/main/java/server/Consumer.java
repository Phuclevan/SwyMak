package server;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.channel.ChannelPool;
import vn.vnpay.channel.ChannelThread;
import vn.vnpay.config.ConfigManager;
import vn.vnpay.server.Main;

import java.util.UUID;

public class Consumer {
    static {
        System.setProperty("log4j.configurationFile", "D:\\Phuclv\\SwyMak\\Consummer\\src\\main\\resources\\config\\log4j2.xml");
    }

    private static Logger logger = LogManager.getLogger(Main.class);
    private static final Integer NUM_OF_CLIENT = 10;

    public static void main(String[] args) throws Exception {
        ThreadContext.put("token", UUID.randomUUID().toString().replace("-", ""));
        ConfigManager.loadRabbiMQ();
        logger.info("Create factory...");
        try (Channel channel = ChannelPool.getInstance().getChannel()) {
            for (int i = 1; i <= NUM_OF_CLIENT; i++) {
                Runnable client = new ChannelThread(channel);
                Thread thread = new Thread(client);
                thread.start();
            }
            logger.debug("Read sms to queue...");
            channel.queueDeclare("aaaaaaaaaaaa", false, false, false, null);
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                    StringBuilder message = new StringBuilder(new String(delivery.getBody(), "UTF-8"));
//                    logger.debug("SMS: " + message);
//                };
//                CancelCallback cancelCallback = consumerTag -> {
//                };
//                String consumerTag = channel.basicConsume("aaaaaaaaaaaa", true, deliverCallback, cancelCallback);

            int i =0;
            while (true){
                if (i<= 60){
                    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), "UTF-8");
                        logger.debug("SMS: " + message);
                    };
                    CancelCallback cancelCallback = consumerTag -> {
                    };
                    String consumerTag = channel.basicConsume("aaaaaaaaaaaa", true, deliverCallback, cancelCallback);
                    logger.info("Read sms success...{}", consumerTag);
                }else{
                    logger.info("out meta");
                }
                i++;
            }
        }
    }
}

