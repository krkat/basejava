package ru.javawebinar.basejava.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section{
    private static final long serialVersionUID = 1L;
    private Link homepage;
    private List<Period> periods;

    public CompanySection() {
    }

    public CompanySection(String name, String website, List<Period> periods) {
        Objects.requireNonNull(periods, "organizations must not be null");
        this.homepage = new Link(name, website);
        this.periods = periods;
    }

    public String getName() {
        return homepage.getName();
    }

    public String getWebsite() {
        return homepage.getUrl();
    }

    public List<Period> getPeriods() {
        return Collections.unmodifiableList(periods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(homepage, that.homepage) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, periods);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(homepage).append("\n");
        for(Period period : periods) {
            stringBuilder.append(period.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
