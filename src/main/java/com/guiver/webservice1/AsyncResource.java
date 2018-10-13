package com.guiver.webservice1;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Response;

@Stateless
@Path("/asyncresource")
public class AsyncResource {

    /*
    JAX-RS distribuye un hilo para "acceptor" para aceptar una conexión. 
    La conexión se acepta y se entrega a un hilo "worker" quien se encarga 
    de procesar la solicitud en segundo plano y enviar el resultado al cliente
    ,con esto se libera el hilo "acceptor" y se devuelve al pool para que pueda ser 
    usado para aceptar más conexiones.
    */
     
    @GET
    @Asynchronous
    public void asyncRestMethod(@Suspended final AsyncResponse asyncResponse){
        String result = heavyLifting();
        asyncResponse.resume(result); //retorna la respuesta al cliente
    }
    
    /*
    En el siguiente código, siempre que el procesamiento de la operación pesada demore 
    más de 40 segundos, se cancela el procesamiento, se liberen los recursos y se llame al 
    método "handleTimeout ()". Finalmente, se devolverá el código de respuesta Http 503.
    */
    @GET
    @Path("/async2")
    @Asynchronous
    public void asyncRestMethod2(@Suspended final AsyncResponse asyncResponse){
        asyncResponse.setTimeoutHandler(new TimeoutHandler(){
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(
                Response.Status.SERVICE_UNAVAILABLE)
                        .entity("TIME OUT!").build());
            }
        });
        asyncResponse.setTimeout(1, TimeUnit.MILLISECONDS);
        
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = heavyLifting();
                asyncResponse.resume(result);
            }
        });
        thread.start();
    }
    
    private String heavyLifting(){
        return "RESULT";
    }
}