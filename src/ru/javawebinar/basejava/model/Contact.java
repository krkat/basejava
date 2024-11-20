package ru.javawebinar.basejava.model;

import java.util.*;

public class Contact {
    private String contact;

    public Contact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact1 = (Contact) o;
        return Objects.equals(contact, contact1.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contact);
    }

    @Override
    public String toString() {
        return contact;
    }
}