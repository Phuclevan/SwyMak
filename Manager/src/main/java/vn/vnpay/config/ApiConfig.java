package vn.vnpay.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApiConfig {
    private String host;
    private String port;

    public ApiConfig() {
    }
}
