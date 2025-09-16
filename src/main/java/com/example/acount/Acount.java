package com.example.acount;

import com.example.reservation.ReservationDAO;

public class Acount {
    private int id;
    private String name;
    private String password;
    private ReservationDAO reservationDAO;

    public Acount(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.reservationDAO = new ReservationDAO(this);
    }

    public int getId() { 
        return id;
    }

    public String getName() { 
        return name;
    }

    public String getEventTime() { 
        return password;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
    public ReservationDAO getReservationDAO() {
    	return reservationDAO;
	}
    
    public boolean comparisonPassword(String input) {
		return input.equals(password);
	}
}