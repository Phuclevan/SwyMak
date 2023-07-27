package vn.vnpay.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class SmsController {

    @GET
    @Path("/getSms")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public String getSms(String sms) {
        return sms.toString();
    }
}
