package com.example.reservation; 

import com.example.acount.Acount;
import com.example.event.Event; 
 
public class Reservation { 
    private int id; 
    private Acount acount; 
    private Event event;

    public Reservation(int id, Acount acount, Event event) {
        this.id = id; 
        this.acount = acount;
        this.event = event; 
    }

    public int getId() { 
        return id; 
    }

    public int getAcountId() { 
        return acount.getId();
    }

    public int getEventId() { 
        return event.getId(); 
    }
    
    public Event getEvent() {
		return event;
	}
    
    public Acount getAcount() {
		return acount;
	}
}