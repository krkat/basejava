package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResumeServlet extends HttpServlet {
    private enum THEME {
        dark, light, purple
    }
    private Storage storage;
    private final Set<String> themes = new HashSet<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
        for (THEME t : THEME.values()) {
            themes.add(t.name());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        boolean isCreate = (uuid == null || uuid.length() == 0);
        String fullName = req.getParameter("fullName");
        Resume r = isCreate ? new Resume(fullName) : storage.get(uuid);
        if (!isCreate) {
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (!HtmlHelper.isEmpty(value)) {
                r.setContact(type, value.trim());
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            r.getSections().remove(type);
            for (Section section : getSections(req, type)) {
                r.setSection(type, section);
            }
        }
        if (isCreate) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        resp.sendRedirect("resume?theme=" + getTheme(req));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        req.setAttribute("theme", getTheme(req));

        if (action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                resp.sendRedirect("resume?theme=" + getTheme(req));
                return;
            }
            case "view" -> r = storage.get(uuid);
            case "edit" -> {
                r = storage.get(uuid);
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    List<Section> sections = r.getSections().get(type);
                    List<Section> emptyFirstCompanies = new ArrayList<>();
                    emptyFirstCompanies.add(CompanySection.EMPTY);
                    if (sections != null) {
                        for (Section section : sections) {
                            List<Period> emptyFirstPeriods = new ArrayList<>();
                            emptyFirstPeriods.add(Period.EMPTY);
                            emptyFirstPeriods.addAll(((CompanySection) section).getPeriods());

                            String name = ((CompanySection) section).getName();
                            String website = ((CompanySection) section).getWebsite();
                            emptyFirstCompanies.add(new CompanySection(name, website, emptyFirstPeriods));
                        }
                    }
                    r.getSections().remove(type);
                    for (Section section : emptyFirstCompanies) {
                        r.setSection(type, section);
                    }
                }
            }
            case "create" -> r = Resume.EMPTY;
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("resume", r);
        req.getRequestDispatcher(
                "view".equals(action) ? "/WEB-INF/jsp/view.jsp" :
                        "/WEB-INF/jsp/edit.jsp").forward(req, resp);
    }

    private String getTheme(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        return themes.contains(theme) ? theme : THEME.light.name();
    }

    private List<Section> getSections(HttpServletRequest req, SectionType type) {
        List<Section> sections = new ArrayList<>();
        String value = req.getParameter(type.name());
        switch (type) {
            case PERSONAL, OBJECTIVE -> {
                if (!HtmlHelper.isEmpty(value)) {
                    sections.add(new TextSection(value));
                }
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                if (!HtmlHelper.isEmpty(value)) {
                    String[] items = value.split("\n");
                    List<String> list = new ArrayList<>();
                    for (String item : items) {
                        if (!item.trim().isEmpty()) {
                            list.add(item.trim());
                        }
                    }
                    if (!list.isEmpty()) {
                        sections.add(new ListSection(list));
                    }
                }
            }
            case EXPERIENCE, EDUCATION -> {
                String[] names = req.getParameterValues(type.name());
                String[] urls = req.getParameterValues(type.name() + "url");
                for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    if (!HtmlHelper.isEmpty(name)) {
                        List<Period> periods = new ArrayList<>();
                        String pfx = type.name() + i;
                        String[] startDates = req.getParameterValues(pfx + "startDate");
                        String[] endDates = req.getParameterValues(pfx + "endDate");
                        String[] positions = req.getParameterValues(pfx + "position");
                        String[] descriptions = req.getParameterValues(pfx + "description");

                        for (int j = 0; j < positions.length; j++) {
                            if (!HtmlHelper.isEmpty(positions[j]) && !HtmlHelper.isEmpty(startDates[j])) {
                                periods.add(new Period(
                                        DateUtil.parse(startDates[j]),
                                        DateUtil.parse(endDates[j]),
                                        positions[j],
                                        descriptions[j]
                                ));
                            }
                        }
                        if (!periods.isEmpty()) {
                            sections.add(new CompanySection(name, urls[i], periods));
                        }
                    }
                }
            }
        }
        return sections;
    }
}