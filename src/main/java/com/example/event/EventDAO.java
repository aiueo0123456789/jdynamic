package com.example.event; 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EventDAO { 
    private static final List<Event> Events = new CopyOnWriteArrayList<>(); 
    private static final AtomicInteger idCounter = new AtomicInteger(0); 
    private static final String DATA_FILE = "Events.dat"; 
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; 

    static { 
        loadEvents(); 
    }

    public List<Event> getAllEvents() { 
        return new ArrayList<>(Events); 
    } 
 
    public Event getEventById(int id) { 
        return Events.stream() 
                .filter(r -> r.getId() == id) 
                .findFirst() 
                .orElse(null); 
    } 
 
    public boolean addEvent(String name, LocalDateTime EventTime) {
        if (isDuplicate(name, EventTime)) {
            return false;
        }
        int id = idCounter.incrementAndGet(); 
        Events.add(new Event(id, name, EventTime)); 
        saveEvents();
        return true;
    }

    public boolean updateEvent(int id, String name, LocalDateTime EventTime) { 
        if (isDuplicate(name, EventTime, id)) { 
            return false; 
        } 
        for (int i = 0; i < Events.size(); i++) { 
            if (Events.get(i).getId() == id) { 
                Events.set(i, new Event(id, name, EventTime)); 
                saveEvents(); 
                return true; 
            } 
        } 
        return false; 
    } 

    public boolean deleteEvent(int id) { 
        boolean removed = Events.removeIf(r -> r.getId() == id); 
        if (removed) { 
            saveEvents(); 
        } 
        return removed; 
    } 

    public void cleanUpPastEvents() { 
        int initialSize = Events.size(); 
        Events.removeIf(r -> r.getEventTime().isBefore(LocalDateTime.now())); 
        if (Events.size() < initialSize) { 
            saveEvents(); 
        } 
    } 

    public List<Event> searchAndSortEvents(String searchTerm, String sortBy, String sortOrder) {
        List<Event> filteredList = Events.stream() 
                .filter(r -> searchTerm == null || searchTerm.trim().isEmpty() || 
                        r.getName().toLowerCase().contains(searchTerm.toLowerCase()) || 
                        r.getEventTime().format(FORMATTER).contains(searchTerm)) 
                .collect(Collectors.toList()); 
 
        Comparator<Event> comparator = null; 
        if ("name".equals(sortBy)) { 
            comparator = Comparator.comparing(Event::getName); 
        } else if ("time".equals(sortBy)) { 
            comparator = Comparator.comparing(Event::getEventTime); 
        } 
 
        if (comparator != null) { 
            if ("desc".equals(sortOrder)) { 
                filteredList.sort(comparator.reversed());    
            } else { 
                filteredList.sort(comparator); 
            } 
        } 
        return filteredList; 
    } 
 
    public void importEvents(BufferedReader reader) throws IOException { 
        String line; 
        while ((line = reader.readLine()) != null) { 
            String[] parts = line.split(","); 
            if (parts.length == 3) { 
                try { 
                    int id = Integer.parseInt(parts[0]); 
                    String name = parts[1]; 
                    LocalDateTime time = LocalDateTime.parse(parts[2], FORMATTER); 
                    if (!isDuplicate(name, time) && getEventById(id) == null) { 
                        Events.add(new Event(id, name, time)); 
                        if (id > idCounter.get()) { 
                            idCounter.set(id); 
                        } 
                    } 
                } catch (NumberFormatException | DateTimeParseException e) { 
                    System.err.println("Skipping invalid CSV line: " + line + " - " + e.getMessage());
                }
            }
        }
        saveEvents();
    }

    private boolean isDuplicate(String name, LocalDateTime time) { 
        return Events.stream().anyMatch(r -> r.getName().equalsIgnoreCase(name) && r.getEventTime().equals(time)); 
    }

    private boolean isDuplicate(String name, LocalDateTime time, int excludeId) {
        return Events.stream().anyMatch(r -> r.getId() != excludeId && r.getName().equalsIgnoreCase(name) && r.getEventTime().equals(time));
    }

    private static void saveEvents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Event res : Events) {
                writer.write(String.format("%d,%s,%s%n", res.getId(), res.getName(), res.getEventTime().format(FORMATTER)));
            }
        } catch (IOException e) {
            System.err.println("Error saving Events: " + e.getMessage());
        }
    }

    private static void loadEvents() { 
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
                        LocalDateTime time = LocalDateTime.parse(parts[2], FORMATTER); 
                        Events.add(new Event(id, name, time)); 
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