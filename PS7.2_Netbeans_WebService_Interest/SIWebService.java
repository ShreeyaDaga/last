package org.si.calculator;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

@WebService(serviceName = "SIWebService")
@Stateless()
public class SIWebService {

    @WebMethod(operationName = "calculateSI")
    public double calculateSI(
        @WebParam(name = "principal") double principal,
        @WebParam(name = "rate")      double rate,
        @WebParam(name = "time")      double time) {

        return (principal * rate * time) / 100;
    }
}