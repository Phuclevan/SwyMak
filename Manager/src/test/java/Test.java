import vn.vnpay.config.ConfigManager;
import vn.vnpay.config.DbConfig;
import vn.vnpay.model.Sms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
//        String des = "^0\\d{9}|^84\\d{9}";
//        String sdt = "8386996283";
//        Pattern pattern = Pattern.compile(des);
//        Matcher matcher = pattern.matcher(sdt);
//        if (matcher.matches()){
//            System.out.println("regex true");
//        }else {
//            System.out.println("false");
//        }
//
//        if (sdt.matches(des)){
//            System.out.println("Dung");
//        }else {
//            System.out.println("sai");
//        }
        ConfigManager.loadDatabase();
        System.out.println("load db done");
        DbConfig dbConfig = ConfigManager.getDbConfig();
        System.out.println(dbConfig.toString());

    }
}
