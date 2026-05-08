package org.si.calculator;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 * Simple Interest Calculator Web Service
 * Formula: SI = (P * R * T) / 100
 */
@WebService(serviceName = "SIWebService")
@Stateless()
public class SIWebService {

    /**
     * Calculates Simple Interest
     * @param principal  - Principal amount
     * @param rate       - Rate of interest per annum
     * @param time       - Time in years
     * @return           - Simple Interest value
     */
    @WebMethod(operationName = "calculateSI")
    public double calculateSI(
            @WebParam(name = "principal") double principal,
            @WebParam(name = "rate")      double rate,
            @WebParam(name = "time")      double time) {

        double si = (principal * rate * time) / 100;
        return si;
    }

    /**
     * Calculates Total Amount (Principal + SI)
     */
    @WebMethod(operationName = "calculateTotalAmount")
    public double calculateTotalAmount(
            @WebParam(name = "principal") double principal,
            @WebParam(name = "rate")      double rate,
            @WebParam(name = "time")      double time) {

        double si = (principal * rate * time) / 100;
        return principal + si;
    }
}