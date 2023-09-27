package vn.vnpay.model;

import lombok.*;

@Getter
@Setter
@ToString
public class Sms {
    private String messageId;
    private String keyword;
    private String sender;
    private String destination;
    private String shortMessage;
    private String partnerCode;

    public Sms(String messageId, String keyword, String sender, String destination, String shortMessage, String partnerCode) {
        this.messageId = messageId;
        this.keyword = keyword;
        this.sender = sender;
        this.destination = destination;
        this.shortMessage = shortMessage;
        this.partnerCode = partnerCode;
    }

    public Sms() {
    }
}
