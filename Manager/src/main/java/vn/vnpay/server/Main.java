package vn.vnpay.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import vn.vnpay.controller.SmsController;

public class Main {
    static {
        System.setProperty("log4j.configurationFile", "D:\\Phuclv\\SwyMak\\Manager\\src\\main\\resources\\config\\log4j2.xml");
    }
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws Exception {


        Server server = new Server(8864);

        ServletContextHandler ctx =
                new ServletContextHandler(ServletContextHandler.SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
        serHol.setInitOrder(0);
        serHol.setInitParameter("jersey.config.server.provider.classnames", SmsController.class.getCanonicalName());
        logger.info("success");
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
