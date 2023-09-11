package vn.vnpay.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class Sms {
    private String messageId;
    private String keyword;
    private String sender;
    private String destination;
    private String shortMessage;
    private String partnerCode;

}
