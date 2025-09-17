package com.example.event; 

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EventDAO {
    private static final List<Event> Events = new CopyOnWriteArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private static final String DATA_FILE = "Events.dat";

    static {
        loadEvents();
    }

    public List<Event> getAllEvents() {
        return Events;
    }

    public Event getEventById(int id) {
        return Events.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public boolean addEvent(String name, LocalDateTime startTime, LocalDateTime endTime) {
        if (isDuplicate(name, startTime, endTime)) {
            return false;
        }
        int id = idCounter.incrementAndGet();
        Events.add(new Event(id, name, startTime, endTime));
        System.out.println("名前" + name + "開催" + startTime + "終了" + endTime);
        return true;
    }

    public boolean deleteEvent(int id) {
        boolean removed = Events.removeIf(r -> r.getId() == id);
        return removed;
    }

    public List<Event> searchAndSortEvents(String searchTerm) {
        List<Event> filteredList = Events.stream().filter(r -> searchTerm == null || searchTerm.trim().isEmpty() || r.getName().toLowerCase().contains(searchTerm.toLowerCase())).collect(Collectors.toList());
        return filteredList;
    }

    private boolean isDuplicate(String name, LocalDateTime statTime, LocalDateTime endTime) { 
        return Events.stream().anyMatch(r -> r.getName().equalsIgnoreCase(name) && r.getStartTime().equals(statTime) && r.getEndTime().equals(endTime)); 
    }

    private static void loadEvents() { 
        File file = new File(DATA_FILE); 
        if (!file.exists()) {
        }
        return;
    } 
} 