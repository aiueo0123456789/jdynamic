package com.example.reservation; 

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.example.acount.Acount;
import com.example.event.Event;

public class ReservationDAO { 
    private static final List<Reservation> Reservations = new CopyOnWriteArrayList<>(); 
    private static final AtomicInteger idCounter = new AtomicInteger(0); 
    private static final String DATA_FILE = "reservations.dat"; 
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private Acount acount;

    static { 
        loadReservations(); 
    }
    
    public ReservationDAO(Acount acount) {
    	this.acount = acount;
	}

    public List<Reservation> getAllReservations() { 
        return Reservations; 
    } 
 
    public Reservation getReservationById(int id) {
        return Reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }
 
    public boolean addReservation(Event event) {
        int id = idCounter.incrementAndGet();
        Reservations.add(new Reservation(id, acount, event));
        return true;
    }

    public boolean deleteReservation(int id) {
        boolean removed = Reservations.removeIf(r -> r.getId() == id);
        return removed;
    }

    public List<Reservation> searchAndSortReservations(String searchTerm, String sortBy, String sortOrder) {
        List<Reservation> filteredList = Reservations.stream().filter(r -> searchTerm == null || searchTerm.trim().isEmpty() || r.getEvent().getName().toLowerCase().contains(searchTerm.toLowerCase()) || r.getEvent().getStartTime().format(FORMATTER).contains(searchTerm)).collect(Collectors.toList());
//        Comparator<Reservation> comparator = null; 
//        if ("name".equals(sortBy)) {
//            comparator = Comparator.comparing(Reservation::getName); 
//        } else if ("time".equals(sortBy)) { 
//            comparator = Comparator.comparing(Reservation::getReservationTime); 
//        }
//
//        if (comparator != null) { 
//            if ("desc".equals(sortOrder)) { 
//                filteredList.sort(comparator.reversed());    
//            } else { 
//                filteredList.sort(comparator); 
//            } 
//        }
        return filteredList;
    }
 
    private boolean isDuplicate(Acount acount, Event event) { 
        return Reservations.stream().anyMatch(r -> r.getEvent() == event && r.getAcount() == acount); 
    }
 
    private static void loadReservations() { 
        File file = new File(DATA_FILE); 
        if (!file.exists()) { 
        }
        return; 
    } 
} 