package com.example.event; 

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<Event> searchAndSortEvents(String searchTerm, String sortBy, String sortOrder) {
        List<Event> filteredList = Events.stream()
                .filter(r -> searchTerm == null || searchTerm.trim().isEmpty() || r.getName().toLowerCase().contains(searchTerm.toLowerCase()) || r.getStartTime().format(FORMATTER).contains(searchTerm))
                .collect(Collectors.toList());

        Comparator<Event> comparator = null;
        if ("name".equals(sortBy)) {
            comparator = Comparator.comparing(Event::getName);
        } else if ("time".equals(sortBy)) {
            comparator = Comparator.comparing(Event::getStartTime);
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