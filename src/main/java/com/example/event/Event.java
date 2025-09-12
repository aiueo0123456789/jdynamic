package com.example.event;

import java.time.LocalDateTime; 

public class Event { 
    private int id; 
    private String name; 
    private LocalDateTime eventTime;

    public Event(int id, String name, LocalDateTime eventTime) {
        this.id = id; 
        this.name = name; 
        this.eventTime = eventTime; 
    }

    public int getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }

    public LocalDateTime getEventTime() { 
        return eventTime; 
    }
}