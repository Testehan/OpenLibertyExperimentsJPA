package com.testehan.openliberty.frontend.client;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class BadRequestExceptionMapper implements ResponseExceptionMapper<BadRequestException> {

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        System.out.println("status = " + status);
        return status == 400;
    }

    @Override
    public BadRequestException toThrowable(Response response) {
        return new BadRequestException(response.readEntity(String.class));
    }
}
