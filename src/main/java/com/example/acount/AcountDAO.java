package com.example.acount; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AcountDAO {
    private static final List<Acount> Acounts = new CopyOnWriteArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private static final String DATA_FILE = "Acounts.dat";
    private Acount activeAcount = null;

    static {
        loadAcounts();
    }
    
    public Acount getActiveAcount() {
		return activeAcount;
	}
    
    public void setActiveAcount(int id) {
    	activeAcount = getAcountById(id);
	}

    public List<Acount> getAllAcounts() { 
        return new ArrayList<>(Acounts); 
    }

    public Acount getAcountById(int id) { 
        return Acounts.stream().filter(r -> r.getId() == id).findFirst().orElse(null); 
    }

    public boolean addAcount(int id, String name, String AcountTime) { 
        Acounts.add(new Acount(id, name, AcountTime));
        return true;
    }

    public boolean updateAcount(Acount acount, String name) { 
    	acount.setName(name);
        return true; 
    }

    public boolean deleteAcount(int id) { 
        return Acounts.removeIf(r -> r.getId() == id);
    }

    private boolean isDuplicate(int id) {
        return Acounts.stream().anyMatch(acount -> acount.getId() == id);
    }

    private static void loadAcounts() { 
        File file = new File(DATA_FILE); 
        if (!file.exists()) { 
            return; 
        } 
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) { 
            String line; 
            int maxId = 0; 
            while ((line = reader.readLine()) != null) { 
                String[] parts = line.split(","); 
                if (parts.length == 3) {
                    try { 
                        int id = Integer.parseInt(parts[0]); 
                        String name = parts[1]; 
                        String password = parts[2]; 
                        Acounts.add(new Acount(id, name, password)); 
                        if (id > maxId) { 
                            maxId = id; 
                        }
                    } catch (NumberFormatException e) { 
                        System.err.println("Skipping invalid data file line (NumberFormatException): " + line + " - " + e.getMessage()); 
                    } catch (DateTimeParseException e) { 
                        System.err.println("Skipping invalid data file line (DateTimeParseException): " + line + " - " + e.getMessage()); 
                    } 
                } 
            } 
            idCounter.set(maxId); 
        } catch (IOException e) { 
            System.err.println("Error loading Events (IOException): " + 
e.getMessage()); 
        } catch (Exception e) { 
            System.err.println("An unexpected error occurred while loading Events: " + 
e.getMessage()); 
            e.printStackTrace(); 
        } 
    }
} 