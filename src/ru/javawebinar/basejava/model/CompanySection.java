package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CompanySection implements Section{
    private final SectionType sectionType;
    private String name;
    private String website;
    private List<Period> periods;

    private CompanySection(CompanySectionBuilder builder) {
        this.sectionType = builder.sectionType;
        this.name = builder.name;
        this.website = builder.website;
        this.periods = builder.periods;
    }

    @Override
    public SectionType getSectionType() {
        return sectionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Period> getPeriods() {
        return Collections.unmodifiableList(periods);
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public void removePeriod(Period period) {
        periods.remove(period);
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return sectionType == that.sectionType && Objects.equals(name, that.name) &&
                Objects.equals(website, that.website) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionType, name, website, periods);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("\n").append(website).append("\n");
        for(Period period : periods) {
            stringBuilder.append(period.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static class CompanySectionBuilder{
        private final SectionType sectionType;
        private String name;
        private String website;
        private List<Period> periods;

        public CompanySectionBuilder(SectionType sectionType, String name) {
            this.sectionType = sectionType;
            this.name = name;
            this.periods = new ArrayList<>();
        }

        public CompanySectionBuilder setWebsite(String website) {
            this.website = website;
            return this;
        }

        public CompanySectionBuilder setPeriods(List<Period> periods) {
            this.periods = periods;
            return this;
        }

        public CompanySection build(){
            return new CompanySection(this);
        }
    }
}
