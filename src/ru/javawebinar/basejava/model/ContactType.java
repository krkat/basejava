package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.HtmlHelper;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + HtmlHelper.toLink("skype:" + value, value);
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink(value);
        }

        @Override
        public String toLink(String value) {
            return (value == null) ? "" : toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль Github") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a class=\"contact-link\" href='" + href + "'>" + title + "</a>";
    }
}
