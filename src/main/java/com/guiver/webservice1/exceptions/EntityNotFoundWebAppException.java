package com.guiver.webservice1.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EntityNotFoundWebAppException  extends WebApplicationException{

    /**
     * Genera una excepcion HTTP 404 (Not Found)
     */
    public EntityNotFoundWebAppException() {
        super(Response.Status.NOT_FOUND);
    }

    /**
     * Genera una excepcion HTTP 404 (Not Found)
     * @param message
     */
    public EntityNotFoundWebAppException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
        .entity(message).type(MediaType.APPLICATION_JSON).build());
    }

    
    
}