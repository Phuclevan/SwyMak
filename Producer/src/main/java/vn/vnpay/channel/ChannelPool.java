package vn.vnpay.channel;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j2;

import vn.vnpay.config.ConfigManager;
import vn.vnpay.config.RabbitMQConfig;
import vn.vnpay.connection.ConnecRabbit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class ChannelPool {
    private static final long EXPIRED_TIME_IN_MILISECOND = 1200;
    private static final int NUMBER_OF_TAXI = 20;
    private  final List<Channel> available = Collections.synchronizedList(new ArrayList<>());
    private  final List<Channel> inUse = Collections.synchronizedList(new ArrayList<>());

    private static final AtomicInteger count = new AtomicInteger(0);
    private static final AtomicBoolean waiting = new AtomicBoolean(false);
    RabbitMQConfig config = ConfigManager.getRabbitConfig();
    ConnectionFactory factory = ConnecRabbit.getInstance().getConnect(config);

    private static final class SingletonHolder {

        private static final ChannelPool INSTANCE = new ChannelPool();
    }
    public static ChannelPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ChannelPool() {
    }

    public synchronized Channel getChannel() {
        if (!available.isEmpty()) {
            Channel channel = available.remove(0);
            inUse.add(channel);
            return channel;
        }
        if (count.get() == NUMBER_OF_TAXI) {
            this.waitingUntilTaxiAvailable();
            return this.getChannel();
        }
        Channel channel = this.createChannel();
        inUse.add(channel);
        return channel;
    }
    public synchronized void release(Channel channel) {
        inUse.remove(channel);
        available.add(channel);
        log.debug("Connect channel: {}", channel.getChannelNumber());
    }

    private Channel createChannel(){
        waiting(200); // The time to create a taxi
        Channel channel = null;
        try {
            channel = factory.newConnection().createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
        }


    private void waitingUntilTaxiAvailable() {
        if (waiting.get()) {
            waiting.set(false);
            throw new ChannelNotFountException("No taxi available");
        }
        waiting.set(true);
        waiting(EXPIRED_TIME_IN_MILISECOND);
    }

    private void waiting(long numberOfSecond) {

        try {
            TimeUnit.MILLISECONDS.sleep(numberOfSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
