package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        boolean isExistResume = !uuid.isEmpty();
        String fullName = req.getParameter("fullName");
        Resume r = isExistResume ? storage.get(uuid) : new Resume(fullName);
        if (isExistResume) {
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.addContact(type, value.trim());
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = req.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                Section section = getSection(type, value);
                if (section != null) {
                    r.getSections().remove(type);
                    r.addSection(type, section);
                }
            } else {
                r.getSections().remove(type);
            }
        }
        if (!isExistResume) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        resp.sendRedirect("resume");
    }

    private Section getSection(SectionType type, String value) {
        switch (type) {
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(value);
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                String[] items = value.split("\n");
                List<String> list = new ArrayList<>();
                for (String item : items) {
                    if (!item.trim().isEmpty()) {
                        list.add(item.trim());
                    }
                }
                return !list.isEmpty() ? new ListSection(list) : null;
            }
            case EXPERIENCE, EDUCATION -> {
                return null;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                resp.sendRedirect("resume");
                return;
            }
            case "view", "edit" -> r = storage.get(uuid);
            case "create" -> r = new Resume();
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("resume", r);
        req.getRequestDispatcher(
                "view".equals(action) ? "/WEB-INF/jsp/view.jsp" :
                        "/WEB-INF/jsp/edit.jsp").forward(req, resp);
    }
}