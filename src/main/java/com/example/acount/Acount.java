package com.example.acount;

public class Acount {
    private int id;
    private String name;
    private String password;

    public Acount(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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
}