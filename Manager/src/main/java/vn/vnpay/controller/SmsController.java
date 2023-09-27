package vn.vnpay.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.service.SmsService;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;


@Log4j2
@Path("/api")
public class SmsController {
    @POST
    @Path("/sendSms")
    @Produces(MediaType.APPLICATION_JSON)
    public String sendSms(String request) {
        String token = UUID.randomUUID().toString().replaceAll("[a-zA-Z-]","");
        ThreadContext.put("token", token);
        SmsService.sendRequest(request);
        return "SUCCESS";
    }
}
