package com.example.main; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.example.acount.Acount;
import com.example.acount.AcountDAO;
import com.example.event.Event;
import com.example.event.EventDAO;
import com.example.reservation.Reservation;
import com.example.reservation.ReservationDAO;

@MultipartConfig
@WebServlet("/main")
public class MainServlet extends HttpServlet { 
    private final EventDAO eventDAO = new EventDAO();
    private final AcountDAO acountDAO = new AcountDAO();

    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	System.out.println("doGet が呼ばれました");
        String action = req.getParameter("action");
        System.out.println("action : " + action);
        req.setAttribute("activeAcount", acountDAO.getActiveAcount());

        if ("eventsList".equals(action) || action == null) { 
            String searchTerm = req.getParameter("search"); 
            String sortBy = req.getParameter("sortBy"); 
            String sortOrder = req.getParameter("sortOrder"); 
            int page = 1; 
            int recordsPerPage = 5;

            if (req.getParameter("page") != null) { 
                page = Integer.parseInt(req.getParameter("page")); 
            }
            List<Event> events = eventDAO.searchAndSortEvents(searchTerm, sortBy, sortOrder);

            int start = (page - 1) * recordsPerPage; 
            int end = Math.min(start + recordsPerPage, events.size()); 
            List<Event> view_events = events.subList(start, end); 

            int noOfPages = (int) Math.ceil(events.size() * 1.0 / recordsPerPage); 

            req.setAttribute("events", view_events);
            req.setAttribute("noOfPages", noOfPages); 
            req.setAttribute("currentPage", page); 
            req.setAttribute("searchTerm", searchTerm); 
            req.setAttribute("sortBy", sortBy); 
            req.setAttribute("sortOrder", sortOrder); 

            RequestDispatcher rd = req.getRequestDispatcher("/jsp/eventsList.jsp"); 
            rd.forward(req, resp);
        } else if ("acountsList".equals(action)) {
        	System.out.println("アカウントリストページに移ります");
        	 String searchTerm = req.getParameter("search"); 
             String sortBy = req.getParameter("sortBy"); 
             String sortOrder = req.getParameter("sortOrder"); 
             int page = 1;
             int recordsPerPage = 5;

             if (req.getParameter("page") != null) { 
                 page = Integer.parseInt(req.getParameter("page")); 
             }
             List<Acount> acounts = acountDAO.searchAndSortAcounts(searchTerm, sortBy, sortOrder);

             int start = (page - 1) * recordsPerPage; 
             int end = Math.min(start + recordsPerPage, acounts.size()); 
             List<Acount> view_acounts = acounts.subList(start, end); 

             int noOfPages = (int) Math.ceil(acounts.size() * 1.0 / recordsPerPage); 

             req.setAttribute("acounts", view_acounts); 
             req.setAttribute("noOfPages", noOfPages); 
             req.setAttribute("currentPage", page); 
             req.setAttribute("searchTerm", searchTerm);
             req.setAttribute("sortBy", sortBy);
             req.setAttribute("sortOrder", sortOrder);

             RequestDispatcher rd = req.getRequestDispatcher("/jsp/acountsList.jsp"); 
             rd.forward(req, resp);
        } else if ("edit".equals(action)) { 
            int id = Integer.parseInt(req.getParameter("id"));
            ReservationDAO reservationDAO = acountDAO.getActiveAcount().getReservationDAO();
            Reservation reservation = reservationDAO.getReservationById(id); 
            req.setAttribute("reservation", reservation); 
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/edit.jsp"); 
            rd.forward(req, resp); 
        } else if ("export_csv".equals(action)) { 
            exportCsv(req, resp);
        } else if ("reservation".equals(action)) {
        	int event_id = Integer.parseInt(req.getParameter("event_id")); 
        	req.setAttribute("event", eventDAO.getEventById(event_id)); 
            req.setAttribute("acount", acountDAO.getActiveAcount());
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/reservationAdd.jsp"); 
            rd.forward(req, resp); 
        } else if ("reservationsList".equals(action)) {
            req.setAttribute("acount", acountDAO.getActiveAcount());
            req.setAttribute("reservations", acountDAO.getActiveAcount());
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/reservationsList.jsp"); 
            rd.forward(req, resp);
        } else if ("eventAdd".equals(action)) {
            req.setAttribute("acount", acountDAO.getActiveAcount());
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/eventAdd.jsp"); 
            rd.forward(req, resp);
        } else { 
            resp.sendRedirect("index.jsp"); 
        } 
    }
 
    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	System.out.println("doPost が呼ばれました");
        req.setCharacterEncoding("UTF-8"); 
        String action = req.getParameter("action");
        System.out.println(action);

        if ("eventAdd".equals(action)) { // イベント追加
        	System.out.println("イベントの追加");
            String name = req.getParameter("event_name");
            String eventStartString = req.getParameter("event_startTime");
            String eventEndString = req.getParameter("event_endTime");
            if (name == null || name.trim().isEmpty()) {
                req.setAttribute("errorMessage", "名前は必須です。");
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp"); 
                rd.forward(req, resp);
                return;
            }
            if (eventStartString == null || eventStartString.isEmpty()) { 
                req.setAttribute("errorMessage", "開催日時は必須です。");
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                rd.forward(req, resp);
                return;
            }
            if (eventEndString == null || eventEndString.isEmpty()) { 
                req.setAttribute("errorMessage", "終了日時は必須です。");
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                rd.forward(req, resp);
                return;
            }
            try { 
                LocalDateTime startTime = LocalDateTime.parse(eventStartString);
                if (startTime.isBefore(LocalDateTime.now())) {
                    req.setAttribute("errorMessage", "過去の日時は選択できません。");
                    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                    rd.forward(req, resp);
                    return;
                }
                LocalDateTime endTime = LocalDateTime.parse(eventStartString);
                if (endTime.isBefore(startTime)) {
                    req.setAttribute("errorMessage", "終了日時が開催日時より前");
                    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                    rd.forward(req, resp);
                    return;
                }
                if (!eventDAO.addEvent(name, startTime, endTime)) {
                    req.setAttribute("errorMessage", "同じ名前と日時での予約は既に存在します。");
                    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                    rd.forward(req, resp);
                    return;
                }
                resp.sendRedirect("main?action=list");
            } catch (DateTimeParseException e) { 
                req.setAttribute("errorMessage", "有効な日時を入力してください。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp"); 
                rd.forward(req, resp);
            }
        } else if ("reservationAdd".equals(action)) { // イベント予約
        	System.out.println("予約の追加");
            int eventId = Integer.parseInt(req.getParameter("event_id"));
            Event event = eventDAO.getEventById(eventId);
            ReservationDAO reservationDAO = acountDAO.getActiveAcount().getReservationDAO();
            try {
                if (!reservationDAO.addReservation(event)) {
                    req.setAttribute("errorMessage", "このイベントはすでに同じアカウントで予約されています。");
                    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                    rd.forward(req, resp);
                    return;
                }
                resp.sendRedirect("main?action=list");
            } catch (DateTimeParseException e) { 
                req.setAttribute("errorMessage", "失敗しました"); 
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp"); 
                rd.forward(req, resp); 
            }
        } else if ("singup".equals(action)) { // 会員登録
        	int id = Integer.parseInt(req.getParameter("acount_id"));
            String name = req.getParameter("acount_name");
            String passwaord = req.getParameter("acount_passwaord"); 

            if (id == 0) { 
                req.setAttribute("errorMessage", "識別名は必須です。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                rd.forward(req, resp); 
                return; 
            }
            if (name == null || name.trim().isEmpty()) { 
                req.setAttribute("errorMessage", "名前は必須です。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                rd.forward(req, resp);
                return;
            }
            if (passwaord == null || passwaord.trim().isEmpty()) { 
                req.setAttribute("errorMessage", "パスワードを設定してください。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                rd.forward(req, resp); 
                return; 
            }
 
            try {
                if (!acountDAO.addAcount(id, name, passwaord)) { 
                    req.setAttribute("errorMessage", "失敗しました。"); 
                    RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                    rd.forward(req, resp); 
                    return;
                }
                acountDAO.setActiveAcount(id);
                resp.sendRedirect("main?action=acountsList");
            } catch (DateTimeParseException e) {
                req.setAttribute("errorMessage", "有効な日時を入力してください。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                rd.forward(req, resp); 
            } 
        } else if ("singin".equals(action)) { // ログイン
        	int id = Integer.parseInt(req.getParameter("acount_id"));
            String passwaord = req.getParameter("acount_passwaord"); 

            if (id == 0) { 
                req.setAttribute("errorMessage", "識別名は必須です。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                rd.forward(req, resp); 
                return; 
            }
            if (passwaord == null || passwaord.trim().isEmpty()) { 
                req.setAttribute("errorMessage", "パスワードを入力れてください。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                rd.forward(req, resp); 
                return; 
            }
 
            try {
                if (!acountDAO.setActiveAcount(id)) { 
                    req.setAttribute("errorMessage", "失敗しました。"); 
                    RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                    rd.forward(req, resp); 
                    return;
                }
                resp.sendRedirect("main?action=acountsList");
            } catch (DateTimeParseException e) {
                req.setAttribute("errorMessage", "有効な日時を入力してください。"); 
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/singup.jsp"); 
                rd.forward(req, resp); 
            } 
        } else if ("eventDelete".equals(action)) { // イベント削除
            int id = Integer.parseInt(req.getParameter("id")); 
            eventDAO.deleteEvent(id); 
            resp.sendRedirect("main?action=eventsList"); 
        } else if ("acountDelete".equals(action)) { // アカウント削除
            int id = Integer.parseInt(req.getParameter("id")); 
            acountDAO.deleteAcount(id);
            resp.sendRedirect("main?action=scountsList"); 
        } else if ("reservationDelete".equals(action)) { // 予約の取り消し
            int id = Integer.parseInt(req.getParameter("id"));
            ReservationDAO reservationDAO = acountDAO.getActiveAcount().getReservationDAO();
            reservationDAO.deleteReservation(id);
            resp.sendRedirect("main?action=reservationsList"); 
        } else if ("import_csv".equals(action)) { 
            try {
                Part filePart = req.getPart("csvFile"); 
                if (filePart != null && filePart.getSize() > 0) { 
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "UTF-8"))) {
//                        reservationDAO.importReservations(reader);
                        req.setAttribute("successMessage", "CSV ファイルのインポートが完了しました。"); 
                    } 
                } 
                else { 
                    req.setAttribute("errorMessage", "インポートするファイルを選択してください。"); 
                }
            } catch (Exception e) { 
                req.setAttribute("errorMessage", "CSV ファイルのインポート中にエラーが発生しました: " + e.getMessage()); 
                e.printStackTrace(); 
            } 
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/eventsList.jsp"); 
            rd.forward(req, resp); 
        } else { 
            resp.sendRedirect("index.jsp"); 
        } 
    } 
 
    private void exportCsv(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
        resp.setContentType("text/csv; charset=UTF-8"); 
        resp.setHeader("Content-Disposition", "attachment; filename=\"reservations.csv\""); 
 
        PrintWriter writer = resp.getWriter(); 
        writer.append("ID,名前,予約日時\n"); 
 
        ReservationDAO reservationDAO = acountDAO.getActiveAcount().getReservationDAO();
        List<Reservation> records = reservationDAO.getAllReservations(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); 
 
//        for (Reservation record : records) { 
//            writer.append(String.format("%d,%s,%s\n", 
//                    record.getId(), 
//                    record.getName(), 
//                    record.getReservationTime() != null ? record.getReservationTime().format(formatter) : "")); 
//        } 
        writer.flush(); 
    } 
}