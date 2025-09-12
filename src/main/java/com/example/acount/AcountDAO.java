package com.example.acount; 

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AcountDAO {
    private static final List<Acount> Acounts = new CopyOnWriteArrayList<>(); 
    private static final AtomicInteger idCounter = new AtomicInteger(0); 
    private static final String DATA_FILE = "Acounts.dat"; 
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 

    static {
        loadAcounts(); 
    }

    public List<Acount> getAllAcounts() { 
        return new ArrayList<>(Acounts); 
    } 
 
    public Acount getAcountById(int id) { 
        return Acounts.stream() 
                .filter(r -> r.getId() == id) 
                .findFirst() 
                .orElse(null); 
    } 
 
    public boolean addAcount(String name, String AcountTime) {
        if (isDuplicate(name, AcountTime)) {
            return false;
        }
        int id = idCounter.incrementAndGet(); 
        Acounts.add(new Acount(id, name, AcountTime)); 
        saveAcounts();
        return true;
    }

    public boolean updateAcount(int id, String name, String AcountTime) { 
        if (isDuplicate(name, AcountTime, id)) { 
            return false; 
        } 
        for (int i = 0; i < Acounts.size(); i++) { 
            if (Acounts.get(i).getId() == id) { 
                Acounts.set(i, new Acount(id, name, AcountTime)); 
                saveAcounts(); 
                return true; 
            } 
        } 
        return false; 
    }

    public boolean deleteAcount(int id) { 
        boolean removed = Acounts.removeIf(r -> r.getId() == id); 
        if (removed) { 
        } 
        return removed; 
    }

    private boolean isDuplicate(String name) { 
        return Acounts.stream().anyMatch(r -> r.getName().equalsIgnoreCase(name) && r.equals(time)); 
    }

    private boolean isDuplicate(String name, String time, int excludeId) {
        return Acounts.stream().anyMatch(r -> r.getId() != excludeId && r.getName().equalsIgnoreCase(name) && r.getAcountTime().equals(time));
    }
} 