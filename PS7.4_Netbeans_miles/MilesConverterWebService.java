package org.miles.service;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

@WebService(serviceName = "MilesConverterWebService")
@Stateless()
public class MilesConverterWebService {

    @WebMethod(operationName = "convertToKilometer")
    public double convertToKilometer(
            @WebParam(name = "miles") double miles) {

        return miles * 1.60934;
    }
}