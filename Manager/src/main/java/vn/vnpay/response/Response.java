package vn.vnpay.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Response {
    private String messageId;
    private String keyword;
    private String sender;
    private String destination;
    private String shortMessage;
    private String partnerCode;
    private String status;
}
