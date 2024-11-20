package ru.javawebinar.basejava.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> items;

    public ListSection(List<String> items) {
        this.items = items;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
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