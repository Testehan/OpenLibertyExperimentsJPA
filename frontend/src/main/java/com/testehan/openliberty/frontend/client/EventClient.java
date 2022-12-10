package com.testehan.openliberty.frontend.client;

import jakarta.enterprise.context.Dependent;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@RegisterRestClient
@RegisterProvider(UnknownUrlExceptionMapper.class)
@RegisterProvider(BadRequestExceptionMapper.class)
@Path("/events")
public interface EventClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    JsonArray getEvents() throws UnknownUrlException;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getEvent(@PathParam("id") int eventId) throws UnknownUrlException;

    @DELETE
    @Path("/{id}")
    void deleteEvent(@PathParam("id") int eventId) throws UnknownUrlException;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void addEvent(@FormParam("name") String name,
                  @FormParam("time") String time,
                  @FormParam("location") String location) throws UnknownUrlException, BadRequestException;

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void updateEvent(@FormParam("name") String name,
                     @FormParam("time") String time, @FormParam("location") String location,
                     @PathParam("id") int id) throws UnknownUrlException, BadRequestException;

}
