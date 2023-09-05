package vn.vnpay.controller;

import vn.vnpay.model.Sms;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/api")
public class SmsController {

    @POST
    @Path("/sendSms")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloUsingJson(Sms sms) {
        return sms.toString();
    }
}
