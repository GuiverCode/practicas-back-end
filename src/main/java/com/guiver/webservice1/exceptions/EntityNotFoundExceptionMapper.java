/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guiver.webservice1.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author guille
 */
@Provider//Para que funcione el mapeo, agregar esta clase al archivo RestAppConfig
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException>{

    //mapea la excepcion al Response
    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
    
}
