package vn.vnpay.channel;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ChannelNotFountException extends RuntimeException{
    private static final long serialVersionUID = -6670953536653728443L;
    public ChannelNotFountException(String message) {
        log.error("Channel false");
    }
}
