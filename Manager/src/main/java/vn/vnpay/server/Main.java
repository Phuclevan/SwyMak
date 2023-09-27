package vn.vnpay.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import vn.vnpay.config.ApiConfig;
import vn.vnpay.config.ConfigCommon;
import vn.vnpay.connection.ConnectManager;
import vn.vnpay.controller.SmsController;
import vn.vnpay.model.Sms;
import vn.vnpay.service.SmsService;

public class Main {
    static {
        System.setProperty("log4j.configurationFile", "D:\\Phuclv\\SwyMak\\Manager\\src\\main\\resources\\config\\log4j2.xml");
    }
    private static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        ConfigCommon.getInstance().loadYaml();
        ConfigCommon.getInstance().loadDatabase();
        ConnectManager.getInstance().initDB();
        ApiConfig config = ConfigCommon.getApiConfig();
        Server server = new Server(Integer.parseInt(config.getPort()));

        ServletContextHandler ctx =
                new ServletContextHandler(ServletContextHandler.SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);
        try {

            SmsService service = new SmsService();

            for (int i=0; i<100; i++){
                Sms sms = new Sms();
                sms.setMessageId("id"+i);
                sms.setKeyword("test010");
                sms.setSender("BDS HAAN0");
                sms.setDestination("03869962830");
                sms.setShortMessage("test sms HAAN0");
                sms.setPartnerCode("9704030");
                SmsService.insertData(sms);
            }

            System.out.println("OK");

        }catch (Exception e){
            e.printStackTrace();
        }


        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
        serHol.setInitOrder(0);
        serHol.setInitParameter("jersey.config.server.provider.classnames", SmsController.class.getCanonicalName());
        logger.info("starrrrrrrrrrrrrrrrrrrrrrrrrt");
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
