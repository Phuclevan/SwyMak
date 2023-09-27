package vn.vnpay.server;



import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.channel.ChannelPool;
import vn.vnpay.channel.ChannelThread;
import vn.vnpay.config.ConfigCommon;

import vn.vnpay.config.ConfigManager;
import vn.vnpay.connection.ConnectManager;
import vn.vnpay.model.Sms;
import vn.vnpay.service.ProducerService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static java.util.Calendar.*;

public class Application {
    static {
        System.setProperty("log4j.configurationFile", "D:\\Phuclv\\SwyMak\\Producer\\src\\main\\resources\\config\\log4j2.xml");
    }
    private static Logger logger = LogManager.getLogger(Application.class);
    private static final String QUEUE_NAME = "aaaaaaaaaaaa";
    private static final Integer NUM_OF_CLIENT = 10;
    public static void main(String[] args) {
        logger.info("Load file config rabbitMQ");
        ConfigManager.getInstance().loadRabbiMQ();
        ConfigCommon.getInstance().loadDatabase();
        ConnectManager.getInstance().initDB();
        ThreadContext.put("token", UUID.randomUUID().toString().replace("-",""));
        Date time = getInstance().getTime();
        System.out.println(time);
        try (Channel channel = ChannelPool.getInstance().getChannel()) {
            for (int i = 1; i <= NUM_OF_CLIENT; i++) {
                Runnable client = new ChannelThread(channel);
                Thread thread = new Thread(client);
                thread.start();
            }
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Start sending messages ... ");
            List<Sms> lstSms = ProducerService.getInstance().getAll();
                for (Sms sms : lstSms){
                    String message = new Gson().toJson(sms);
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                }
            System.out.println("sendData from Rabbit success.....");
               Date process = Calendar.getInstance().getTime();
            System.out.println(process);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
