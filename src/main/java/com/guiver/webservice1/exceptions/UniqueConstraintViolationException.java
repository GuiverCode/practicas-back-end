package com.guiver.webservice1.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UniqueConstraintViolationException extends WebApplicationException{

    /**
     * Genera una excepcion HTTP 400 (Bad Request)
     */
    public UniqueConstraintViolationException() {
        super(Response.Status.BAD_REQUEST);
    }

     /**
     * Genera una excepcion HTTP 400 (Bad Request)
     * @param message 
     */
    public UniqueConstraintViolationException(String message) {
         super(Response.status(Response.Status.BAD_REQUEST)
        .entity(message).type(MediaType.APPLICATION_JSON).build());
    }

}