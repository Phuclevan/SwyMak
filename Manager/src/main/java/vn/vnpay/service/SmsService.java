package vn.vnpay.service;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.connection.ConnectManager;
import vn.vnpay.model.Sms;
import vn.vnpay.response.RespCode;
import vn.vnpay.response.Response;


import java.sql.CallableStatement;
import java.sql.Connection;



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
        log.info("Input : {}", request);
        RespCode respCode = checkInput(sms);
        if (!respCode.description().equals("SUCCESS")){
            log.info("Request INVALID_DATA3");
            response.setStatus(respCode.code());
            return respCode.description();
        }
        log.debug("Begin insert data from database oracle...");
        Sms smsnew;
        for (int i=0; i<100; i++){
            smsnew = new Sms();
            smsnew.setMessageId("id" + i);
            smsnew.setDestination(sms.getDestination() + i);
            smsnew.setKeyword(sms.getKeyword() + i);
            smsnew.setSender(sms.getSender() + i);
            smsnew.setShortMessage(sms.getShortMessage() + i);
            smsnew.setPartnerCode(sms.getPartnerCode() + i);
            SmsService.insertData(smsnew);
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
                || !sms.getDestination().matches(REGEX_DESTINATION)
                ){
            log.info("INVALID_DATA");
            return RespCode.INVALID_DATA;
        }
        return RespCode.SUCCESS;
    }
    public static void insertData (Sms sms){
        Connection conn = null;
        CallableStatement st;
        // insert cả các trường thời gian vào db
        try {
            String INSERT_DATA = "BEGIN PKG_SMS.INSERTDATA_SMS(?,?,?,?,?,?); END;";
            conn = ConnectManager.getInstance().getConnectionOnline();
            if (conn == null) {
                log.error("Get Connection to Oracle Database Online failed. Sql: {}", INSERT_DATA);
            }
            //sms.setMessageId(ThreadContext.get("token"));
            st = conn.prepareCall(INSERT_DATA);
            st.setString(1, sms.getMessageId());
            st.setString(2, sms.getDestination());
            st.setString(3, sms.getKeyword());
            st.setString(4, sms.getSender());
            st.setString(5, sms.getShortMessage());
            st.setString(6, sms.getPartnerCode());
            log.info("Begin insert data {}", sms.toString());
            st.execute();
            log.info("Insert data success");
        } catch (Exception e) {
            log.error("Insert false", e);
        }finally {
            ConnectManager.getInstance().closeDBOnline(conn);
        }
    }
}
