package org.calculator.service;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

@WebService(serviceName = "CalculatorWebService")
@Stateless()
public class CalculatorWebService {

    @WebMethod(operationName = "add")
    public double add(@WebParam(name = "a") double a,
                      @WebParam(name = "b") double b) {
        return a + b;
    }

    @WebMethod(operationName = "subtract")
    public double subtract(@WebParam(name = "a") double a,
                           @WebParam(name = "b") double b) {
        return a - b;
    }

    @WebMethod(operationName = "multiply")
    public double multiply(@WebParam(name = "a") double a,
                           @WebParam(name = "b") double b) {
        return a * b;
    }

    @WebMethod(operationName = "divide")
    public double divide(@WebParam(name = "a") double a,
                         @WebParam(name = "b") double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero not allowed!");
        }
        return a / b;
    }
}