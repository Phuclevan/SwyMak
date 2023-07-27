package vn.vnpay.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import vn.vnpay.controller.SmsController;

public class Main {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8864);

        ServletContextHandler ctx =
                new ServletContextHandler(ServletContextHandler.SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
        serHol.setInitOrder(0);
        serHol.setInitParameter("jersey.config.server.provider.classnames", SmsController.class.getCanonicalName());

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
