package com.guiver.webservice1.exceptions;
/*
Esta clase es la encargada de manejar las excepciones de validacion
Intercepta todas las excepciones que violan los constraints de validaciones y los agrega al response
*/
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>{

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ValidationError> errors = exception.getConstraintViolations().stream()
                .map(this::toValidationError)
                .collect(Collectors.toList());
        GenericEntity<List<ValidationError>> listErrors = new GenericEntity<List<ValidationError>>(errors){};
       
         return Response.status(Response.Status.BAD_REQUEST).entity(listErrors)
                       .type(MediaType.APPLICATION_JSON).build();
       
    }

    private ValidationError toValidationError(ConstraintViolation constraintViolation){
        ValidationError error= new ValidationError();
        
        error.setPath(constraintViolation.getPropertyPath().toString());
        error.setMessage(constraintViolation.getMessage());
        return error;
    }
}