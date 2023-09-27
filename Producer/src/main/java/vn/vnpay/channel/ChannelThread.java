package vn.vnpay.channel;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Log4j2
public class ChannelThread implements Runnable{
    private Channel channel;

    public ChannelThread(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        takeChannel();
    }
    private void takeChannel() {
        try {
            log.debug("Create new thread.... {}", Thread.currentThread().getName());

            Channel channel = ChannelPool.getInstance().getChannel();
            TimeUnit.MILLISECONDS.sleep(randInt(1000, 1500));
            ChannelPool.getInstance().release(channel);
            log.debug("Served the thread: {}", Thread.currentThread().getName());
        } catch (InterruptedException | ChannelNotFountException e) {
            log.debug("Rejected the thread: {}", Thread.currentThread().getName());
        }
    }
    public static int randInt(int min, int max) {

        return new Random().nextInt((max - min) + 1) + min;
    }
}
