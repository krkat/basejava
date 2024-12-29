package ru.javawebinar.basejava.util;

public class HtmlHelper {
    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
