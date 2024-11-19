package ru.javawebinar.basejava.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private final SectionType sectionType;
    private List<String> items;

    public ListSection(SectionType sectionType, List<String> items) {
        this.sectionType = sectionType;
        this.items = items;
    }

    @Override
    public SectionType getSectionType() {
        return sectionType;
    }

    public List<String> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void deleteItem(String item) {
        items.remove(item);
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object resume) {
        if (this == resume) return true;
        if (resume == null || getClass() != resume.getClass()) return false;
        ListSection that = (ListSection) resume;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(items);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : items) {
            stringBuilder.append("*").append(item).append("\n");
        }
        return stringBuilder.toString();
    }
}