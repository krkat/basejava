package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");

        R1.setContact(ContactType.EMAIL, "mail1@ya.ru");
        R1.setContact(ContactType.PHONE, "11111");

        R4.setContact(ContactType.PHONE, "44444");
        R4.setContact(ContactType.SKYPE, "Skype");

        R1.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.setSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.setSection(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>(Arrays.asList("Achievement11", "Achievement12", "Achievement13"))));
        R1.setSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList("Java", "SQL", "JavaScript"))));

        List<Period> periods1 = new ArrayList<>();
        periods1.add(new Period(DateUtil.of(2005, Month.JANUARY), DateUtil.NOW, "position1", "content1"));
        periods1.add(new Period(DateUtil.of(2001, Month.MARCH), DateUtil.of(2005, Month.JANUARY), "position2", "content2"));
        R1.setSection(SectionType.EXPERIENCE,
                new CompanySection("Organization11", "http://Organization11.ru", periods1));

        List<Period> periods2 = new ArrayList<>();
        periods2.add(new Period(DateUtil.of(2015, Month.JANUARY), DateUtil.NOW, "position1", "content1"));
        R1.setSection(SectionType.EXPERIENCE,
                new CompanySection("Organization2", "http://Organization2.ru", periods2));

        List<Period> periods3 = new ArrayList<>();
        periods3.add(new Period(DateUtil.of(1996, Month.JANUARY), DateUtil.of(2000, Month.DECEMBER), "aspirant", null));
        periods3.add(new Period(DateUtil.of(2001, Month.MARCH), DateUtil.of(2005, Month.JANUARY), "student", "IT facultet"));
        R1.setSection(SectionType.EDUCATION,
                new CompanySection("Institute", null, periods3));
        /*R1.addSection(SectionType.EDUCATION,
                new CompanySection("Organization12", "http://Organization12.ru", null));*/


        R2.setContact(ContactType.SKYPE, "skype2");
        R2.setContact(ContactType.PHONE, "22222");
    }
}
