package com.testehan.openliberty.frontend.client;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class UnknownUrlExceptionMapper implements ResponseExceptionMapper<UnknownUrlException> {

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        System.out.println("status = " + status);
        return status == 404;
    }

    @Override
    public UnknownUrlException toThrowable(Response response) {
        return new UnknownUrlException();
    }
}
