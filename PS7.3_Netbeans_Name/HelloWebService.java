package org.hello.service;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

@WebService(serviceName = "HelloWebService")
@Stateless()
public class HelloWebService {

    @WebMethod(operationName = "sayHello")
    public String sayHello(
            @WebParam(name = "userName") String userName) {

        return "Hello " + userName;
    }
}