package com.lmig.gde.microservice.fooservice;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.GregorianCalendar;

@RestController
public class SimpleRestController {
    @ApiOperation("Get local server time")
    @RequestMapping(value = "getServerLocalTime", method = RequestMethod.GET)
    public ResponseEntity<String> getServerLocalTime() {
        return new ResponseEntity<String>((new GregorianCalendar().getTime().toString() + " -- response from foo service"), HttpStatus.OK);
    }

}
