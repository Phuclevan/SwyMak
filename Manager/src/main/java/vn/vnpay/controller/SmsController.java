package vn.vnpay.controller;

import lombok.extern.log4j.Log4j2;



import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Log4j2
@Path("/api")
public class SmsController {
    @POST
    @Path("/sendSms")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendSms(String request) {


        return null;
    }
}
