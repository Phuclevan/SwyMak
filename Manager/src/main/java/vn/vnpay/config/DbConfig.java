package vn.vnpay.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DbConfig {
    private int IDLE_TIMEOUT;
    private int MAXIMUM_POOL_SIZE;
    private int MINIMUM_IDLE;
    private String DriverClassName;
    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;
    public DbConfig() {
    }
}
