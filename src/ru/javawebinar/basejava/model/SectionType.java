package ru.javawebinar.basejava.model;

import java.util.List;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(List<Section> value) {
        StringBuilder builder = new StringBuilder("<br>").append(title).append("<br>");
        for (Section section : value) {
            if (section instanceof TextSection) {
                builder.append(((TextSection) section).getContent());
            } else if (section instanceof ListSection) {
                List<String> items = ((ListSection) section).getItems();
                builder.append("<ul>");
                for (String item : items) {
                    builder.append("<li>");
                    builder.append(item).append("</li>");
                }
                builder.append("</ul>");
            } else if (section instanceof CompanySection) {
                String name = ((CompanySection) section).getName();
                String website = ((CompanySection) section).getWebsite();
                List<Period> periods = ((CompanySection) section).getPeriods();
                builder.append(toLink(website, name)).append("<br>");
                for (Period period : periods) {
                    builder.append(period.getStartDate().toString()).append("-");
                    builder.append(period.getEndDate().toString()).append("<br>");
                    builder.append(period.getPosition()).append("<br>");
                    builder.append(period.getDescription()).append("<br>");
                }
            }
        }
        return builder.toString();
    }

    public String toHtml(List<Section> value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}