package vn.vnpay.config;


import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Log4j2
public class ConfigManager {
    private static ApiConfig apiConfig;
    private static DbConfig dbConfig;

    public static boolean loadYaml() {
        try {
            YamlReader reader = new YamlReader(new FileReader("D:\\Phuclv\\SwyMak\\Manager\\src\\main\\resources\\config\\api.yaml"));
            apiConfig = reader.read(ApiConfig.class);
            reader.close();
            return apiConfig != null;
        } catch (YamlException | FileNotFoundException e) {
            log.error("Read file api.yaml....fail: ", e);
            return false;
        } catch (IOException e) {
            log.error("Read file api.yaml....fail: ", e);
            return false;
        }
    }

    public static boolean loadDatabase() {
        try {
            YamlReader reader = new YamlReader(new FileReader("D:\\Phuclv\\SwyMak\\Manager\\src\\main\\resources\\config\\database.yaml"));
            dbConfig = reader.read(DbConfig.class);
            reader.close();
            return dbConfig != null;
        } catch (YamlException | FileNotFoundException e) {
            log.error("Read file database.yaml....fail: ", e);
            return false;
        } catch (IOException e) {
            log.error("Read file database.yaml....fail: ", e);
            return false;
        }
    }

    public static ApiConfig getApiConfig (){
        return apiConfig;
    }

    public static DbConfig getDbConfig (){
        return dbConfig;
    }
}
