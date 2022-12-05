package com.testehan.openliberty.backend.api;

import com.testehan.openliberty.backend.dao.EventDAO;
import com.testehan.openliberty.backend.models.Event;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("events")
public class EventAPI {

    @Inject
    private EventDAO eventDAO;

    /**
     * This method creates a new event from the submitted data (name, time and
     * location) by the user.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addNewEvent(@FormParam("name") String name,
                                @FormParam("time") String time,
                                @FormParam("location") String location) {
        Event newEvent = new Event(name, location, time);
        if (!eventDAO.findEvent(name, location, time).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Event already exists").build();
        }
        eventDAO.createEvent(newEvent);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * This method updates a new event from the submitted data (name, time and location) by the user.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateEvent(@FormParam("name") String name,
                                @FormParam("time") String time,
                                @FormParam("location") String location,
                                @PathParam("id") int id) {
        final Event prevEvent = eventDAO.readEvent(id);
        if (prevEvent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Event with id=" + id + " does not exist").build();
        }
        if (!eventDAO.findEvent(name, location, time).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Event already exists").build();
        }
        prevEvent.setName(name);
        prevEvent.setLocation(location);
        prevEvent.setTime(time);

        eventDAO.updateEvent(prevEvent);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * This method deletes a specific existing/stored event
     */
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteEvent(@PathParam("id") int id) {
        final Event event = eventDAO.readEvent(id);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Event does not exist").build();
        }
        eventDAO.deleteEvent(event);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * This method returns a specific existing/stored event in Json format
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject getEvent(@PathParam("id") int eventId) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Event event = eventDAO.readEvent(eventId);
        if (event != null) {
            builder.add("name", event.getName()).add("time", event.getTime())
                   .add("location", event.getLocation()).add("id", event.getId());
        }
        return builder.build();
    }

    /**
     * This method returns the existing/stored events in Json format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray getEvents() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (Event event : eventDAO.readAllEvents()) {
            builder.add("name", event.getName()).add("time", event.getTime())
                    .add("location", event.getLocation()).add("id", event.getId());
            finalArray.add(builder.build());
        }
        return finalArray.build();
    }
}
