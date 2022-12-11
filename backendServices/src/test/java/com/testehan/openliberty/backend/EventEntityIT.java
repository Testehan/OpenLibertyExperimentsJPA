package com.testehan.openliberty.backend;

import com.testehan.openliberty.backend.models.Event;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EventEntityIT extends EventIT {

    private static final String JSONFIELD_LOCATION = "location";
    private static final String JSONFIELD_NAME = "name";
    private static final String JSONFIELD_TIME = "time";
    private static final String EVENT_TIME = "12:00 PM, January 1 2018";
    private static final String EVENT_LOCATION = "IBM";
    private static final String EVENT_NAME = "JPA Guide";
    private static final String UPDATE_EVENT_TIME = "12:00 PM, February 1 2018";
    private static final String UPDATE_EVENT_LOCATION = "IBM Updated";
    private static final String UPDATE_EVENT_NAME = "JPA Guide Updated";

    private static final String CONTEXT_ROOT = "backend";

    private static final int NO_CONTENT_CODE = Response.Status.NO_CONTENT.getStatusCode();
    private static final int NOT_FOUND_CODE = Response.Status.NOT_FOUND.getStatusCode();

    @BeforeAll
    public static void oneTimeSetup() {
        port = System.getProperty("backend.http.port");
        baseUrl = "http://localhost:" + port + "/" + CONTEXT_ROOT + "/";
    }

    @BeforeEach
    public void setup() {
        form = new Form();
        client = ClientBuilder.newClient();

        eventForm = new HashMap<>();

        eventForm.put(JSONFIELD_NAME, EVENT_NAME);
        eventForm.put(JSONFIELD_LOCATION, EVENT_LOCATION);
        eventForm.put(JSONFIELD_TIME, EVENT_TIME);
    }

    @Test
    public void testInvalidRead() {
        assertTrue(getIndividualEvent(-1).isEmpty(), "Reading an event that does not exist should return an empty list");
    }

    @Test
    public void testInvalidDelete() {
        int deleteResponse = deleteRequest(-1);
        assertEquals(NOT_FOUND_CODE, deleteResponse,
                "Trying to delete an event that does not exist should return the HTTP response code " + NOT_FOUND_CODE);
    }

    @Test
    public void testInvalidUpdate() {
        int updateResponse = updateRequest(eventForm, -1);
        assertEquals(NOT_FOUND_CODE, updateResponse,
                "Trying to update an event that does not exist should return the HTTP response code " + NOT_FOUND_CODE);
    }

    @Test
    public void testReadIndividualEvent() {
        int postResponse = postRequest(eventForm);
        assertEquals(NO_CONTENT_CODE, postResponse, "Creating an event should return the HTTP reponse code " + NO_CONTENT_CODE);

        Event e = new Event(EVENT_NAME, EVENT_LOCATION, EVENT_TIME);
        JsonObject event = findEvent(e);
        event = getIndividualEvent(event.getInt("id"));
        assertData(event, EVENT_NAME, EVENT_LOCATION, EVENT_TIME);

        int deleteResponse = deleteRequest(event.getInt("id"));
        assertEquals(NO_CONTENT_CODE, deleteResponse, "Deleting an event should return the HTTP response code " + NO_CONTENT_CODE);
    }

    @Test
    public void testCRUD() {
        int eventCount = getRequest().size();
        int postResponse = postRequest(eventForm);
        assertEquals(NO_CONTENT_CODE, postResponse, "Creating an event should return the HTTP reponse code " + NO_CONTENT_CODE);

        Event e = new Event(EVENT_NAME, EVENT_LOCATION, EVENT_TIME);
        JsonObject event = findEvent(e);
        assertData(event, EVENT_NAME, EVENT_LOCATION, EVENT_TIME);

        eventForm.put(JSONFIELD_NAME, UPDATE_EVENT_NAME);
        eventForm.put(JSONFIELD_LOCATION, UPDATE_EVENT_LOCATION);
        eventForm.put(JSONFIELD_TIME, UPDATE_EVENT_TIME);
        int updateResponse = updateRequest(eventForm, event.getInt("id"));
        assertEquals(NO_CONTENT_CODE, updateResponse,"Updating an event should return the HTTP response code " + NO_CONTENT_CODE);

        e = new Event(UPDATE_EVENT_NAME, UPDATE_EVENT_LOCATION, UPDATE_EVENT_TIME);
        event = findEvent(e);
        assertData(event, UPDATE_EVENT_NAME, UPDATE_EVENT_LOCATION, UPDATE_EVENT_TIME);

        int deleteResponse = deleteRequest(event.getInt("id"));
        assertEquals(NO_CONTENT_CODE, deleteResponse, "Deleting an event should return the HTTP response code " + NO_CONTENT_CODE);
        assertEquals(eventCount, getRequest().size(),
                "Total number of events stored should be the same after testing CRUD operations.");
    }


    @AfterEach
    public void teardown() {
        response.close();
        client.close();
    }

}
