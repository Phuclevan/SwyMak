package vn.vnpay.service;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.model.Sms;
import vn.vnpay.response.RespCode;
import vn.vnpay.response.Response;

import java.util.Random;
import java.util.UUID;


@Log4j2
public class SmsService {

    public static String sendRequest(String request){
        Sms sms = new Gson().fromJson(request, Sms.class);
        Response response = Response.builder().build();
        if (sms== null){
            log.info("Reques is null");
            response.setStatus(RespCode.INVALID_DATA.code());
            return RespCode.INVALID_DATA.description();
        }
        String token = UUID.randomUUID().toString().replaceAll("-","");
        ThreadContext.put("TOKEN", token);
        log.info("Input : {}", request);
        RespCode respCode = checkInput(sms);
        if (!respCode.description().equals("SUCCESS")){
            log.info("Request INVALID_DATA");
            response.setStatus(respCode.code());
            return respCode.description();
        }


        return null;
    }

    private static RespCode checkInput(Sms sms){

        if (sms.getDestination().isEmpty()
                || sms.getKeyword().isEmpty()
                || sms.getSender().isEmpty()
                || sms.getMessageId().isEmpty()
                || sms.getShortMessage().isEmpty()
        ){
            log.info("INVALID_DATA");
            return RespCode.INVALID_DATA;
        }
        if (sms.getShortMessage().length() >1000){
            log.info("INVALID_DATA");
            return RespCode.INVALID_DATA;
        }
        String REGEX_DESTINATION = "^0\\d{9}|^84\\d{9}";
        if (sms.getDestination().length()<10
                || sms.getDestination().length() >11
                || sms.getDestination().matches(REGEX_DESTINATION)
                ){
            log.info("INVALID_DATA");
            return RespCode.INVALID_DATA;
        }
        log.debug("");
        return RespCode.SUCCESS;
    }
}
