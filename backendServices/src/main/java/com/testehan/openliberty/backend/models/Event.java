package com.testehan.openliberty.backend.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Event")
@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")
@NamedQuery(name = "Event.findEvent", query = "SELECT e FROM Event e WHERE e.name = :name AND e.location = :location AND e.time = :time")
public class Event {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "eventId")
    private int id;

    @Column(name = "eventLocation")
    private String location;
    @Column(name = "eventTime")
    private String time;
    @Column(name = "eventName")
    private String name;

    public Event() {
    }

    public Event(String name, String location, String time) {
        this.name = name;
        this.location = location;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(getLocation(), event.getLocation()) && Objects.equals(getTime(), event.getTime()) && Objects.equals(getName(), event.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocation(), getTime(), getName());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
