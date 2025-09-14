package com.example.event;

import java.time.LocalDateTime; 

public class Event { 
    private int id; 
    private String name; 
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Event(int id, String name, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id; 
        this.name = name; 
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }

    public LocalDateTime getStartTime() { 
        return startTime; 
    }
    
    public LocalDateTime getEndTime() { 
        return endTime; 
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setStartTime(LocalDateTime startTime) {
    	this.startTime = startTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
    	this.endTime = endTime;
    }
}