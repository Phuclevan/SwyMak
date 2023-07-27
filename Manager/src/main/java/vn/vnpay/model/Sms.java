package vn.vnpay.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Sms {
    private String messageId;
    private String keyword;
    private String sender;
    private String destination;
    private String shortMessage;
    private String partnerCode;


}
