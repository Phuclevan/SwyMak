package vn.vnpay.service;

import lombok.extern.log4j.Log4j2;
import oracle.jdbc.OracleTypes;
import vn.vnpay.connection.ConnectManager;
import vn.vnpay.model.Sms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
public class ProducerService {

    private static final class SingletonHolder {

        private static final ProducerService INSTANCE = new ProducerService();
    }
    public static ProducerService getInstance() {
        return ProducerService.SingletonHolder.INSTANCE;
    }

    private ProducerService() {
    }

    public List<Sms> getAll() {
        List<Sms> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        CallableStatement st;
        try {
            String GETALL_DATA = "BEGIN PKG_SMS.GETALL_SMS(?); END;";
            conn = ConnectManager.getInstance().getConnectionOnline();
            if (conn == null) {
                log.error("Get Connection to Oracle Database Online failed. Sql: {}", GETALL_DATA);
            }
            Sms sms;
            st = conn.prepareCall(GETALL_DATA);
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.execute();
            log.info("Begin getall data");
            rs = (ResultSet) st.getObject(1);
            while (rs.next()) {
                sms = new Sms();
                sms.setMessageId(rs.getString("MESSAGE_ID"));
                sms.setDestination(rs.getString("DESTINATION"));
                sms.setSender(rs.getString("SENDER"));
                sms.setKeyword(rs.getString("KEYWORD"));
                sms.setShortMessage(rs.getString("MESSAGE"));
                sms.setPartnerCode(rs.getString("PARTNER_CODE"));
                list.add(sms);
            }
            log.info("Get all data success");
            return list;
        } catch (Exception e) {
            log.error("get all data false", e);
            return Collections.EMPTY_LIST;
        } finally {
            ConnectManager.getInstance().closeDBOnline(conn);
        }
    }
}
