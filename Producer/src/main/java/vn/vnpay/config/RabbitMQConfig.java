package vn.vnpay.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RabbitMQConfig {
    private String host;
    private Integer port;
    private String user;
    private String pass;
    private String QUEUE_NAME;

    public RabbitMQConfig(String host, Integer port, String user, String pass, String QUEUE_NAME) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.QUEUE_NAME = QUEUE_NAME;
    }

    public RabbitMQConfig() {
    }

}
