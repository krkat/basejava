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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        boolean isCreate = (uuid == null || uuid.length() == 0);
        String fullName = req.getParameter("fullName");
        Resume r = isCreate ? new Resume(fullName) : storage.get(uuid);
        if (!isCreate) {
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.setContact(type, value.trim());
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            if (type != SectionType.EXPERIENCE && type != SectionType.EDUCATION) {
                String value = req.getParameter(type.name());
                if (value != null && !value.trim().isEmpty()) {
                    Section section = getSection(type, value);
                    if (section != null) {
                        r.getSections().remove(type);
                        r.setSection(type, section);
                    }
                } else {
                    r.getSections().remove(type);
                }
            } else {
                String[] names = req.getParameterValues(type.name());
                List<Section> sections = new ArrayList<>();
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
                            if (!HtmlHelper.isEmpty(positions[j])) {
                                periods.add(new Period(
                                        DateUtil.parse(startDates[j]),
                                        DateUtil.parse(endDates[j]),
                                        positions[j],
                                        descriptions[j]
                                ));
                            }
                        }
                        sections.add(new CompanySection(name, urls[i], periods));
                    }
                }
                r.getSections().remove(type);
                for (Section section : sections) {
                    r.setSection(type, section);
                }
            }
        }
        if (isCreate) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        resp.sendRedirect("resume");
    }

    private void addSection(HttpServletRequest req, SectionType type, Map<String, Section> sections, String index, String sectionName, String url) {
        String startDate = req.getParameter(type.name() + index + "startDate");
        String endDate = req.getParameter(type.name() + index + "endDate");
        String position = req.getParameter(type.name() + index + "position");
        position = position == null ? "" : position;
        String description = req.getParameter(type.name() + index + "description");
        description = description == null ? "" : description;
        if (!HtmlHelper.isEmpty(startDate) && !HtmlHelper.isEmpty(endDate)) {
            Period period = new Period(DateUtil.parse(startDate), DateUtil.parse(endDate), position, description);
            if (!sections.containsKey(sectionName)) {
                List<Period> periods = new ArrayList<>();
                periods.add(period);
                Section section = new CompanySection(sectionName, url, periods);
                sections.put(sectionName, section);
            } else {
                ((CompanySection) sections.get(sectionName)).getPeriods().add(period);
            }
        }
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
}