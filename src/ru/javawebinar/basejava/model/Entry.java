package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {
    @XmlElement
    private SectionType key;
    @XmlElement
    private List<Section> sections = new ArrayList<>();

    public SectionType getKey() {
        return key;
    }

    public void setKey(SectionType key) {
        this.key = key;
    }

    public List<Section> getSections() {
        return sections;
    }
}
