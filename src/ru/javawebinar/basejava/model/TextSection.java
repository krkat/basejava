package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection implements Section{
    private final SectionType sectionType;
    private String text;

    public TextSection(SectionType sectionType, String text) {
        this.sectionType = sectionType;
        this.text = text;
    }

    @Override
    public SectionType getSectionType() {
        return sectionType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
