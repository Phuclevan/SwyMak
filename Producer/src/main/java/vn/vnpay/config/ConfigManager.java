package vn.vnpay.config;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Log4j2
public class ConfigManager {
    private static RabbitMQConfig rabbitMQConfig;
    private static final class SingletonHolder {

        private static final ConfigManager INSTANCE = new ConfigManager();
    }
    public static ConfigManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ConfigManager() {
    }

    public static boolean loadRabbiMQ() {
        try {
            YamlReader reader = new YamlReader(new FileReader("D:\\Phuclv\\SwyMak\\Producer\\src\\main\\resources\\config\\rabbitMQ.yaml"));
            rabbitMQConfig = reader.read(RabbitMQConfig.class);
            reader.close();
            return rabbitMQConfig != null;
        } catch (YamlException | FileNotFoundException e) {
            log.error("Read file api.yaml....fail: ", e);
            return false;
        } catch (IOException e) {
            log.error("Read file api.yaml....fail: ", e);
            return false;
        }
    }

    public static RabbitMQConfig getRabbitConfig (){
        return rabbitMQConfig;
    }
}
