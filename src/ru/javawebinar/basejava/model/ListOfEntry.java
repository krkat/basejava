package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListOfEntry {
    private List<Entry> list = new ArrayList<>();

    public List<Entry> getList() {
        return list;
    }
}
