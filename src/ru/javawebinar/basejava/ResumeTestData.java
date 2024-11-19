package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        addContacts(resume);
        addTextSections(resume);
        addListSections(resume);
        addCompanySections(resume);

        resume.print();
    }

    private static void addContacts(Resume resume) {
        resume.addContact(new Contact(ContactType.PHONE, ResumeInfo.PHONE));
        resume.addContact(new Contact(ContactType.PHONE, ResumeInfo.PHONE_2));
        resume.addContact(new Contact(ContactType.SKYPE, ResumeInfo.SKYPE));
        resume.addContact(new Contact(ContactType.EMAIL, ResumeInfo.EMAIL));
        resume.addContact(new Contact(ContactType.LINKEDIN, ResumeInfo.LINKEDIN));
        resume.addContact(new Contact(ContactType.GITHUB, ResumeInfo.GITHUB));
        resume.addContact(new Contact(ContactType.STACKOVERFLOW, ResumeInfo.STACKOVERFLOW));
        resume.addContact(new Contact(ContactType.HOME_PAGE, ResumeInfo.HOME_PAGE));
    }

    private static void addTextSections(Resume resume) {
        Section position = new TextSection(SectionType.OBJECTIVE, ResumeInfo.OBJECTIVE);
        resume.addSection(position);
        Section personal = new TextSection(SectionType.PERSONAL, ResumeInfo.PERSONAL);
        resume.addSection(personal);
    }

    private static void addListSections(Resume resume) {
        Section achievement = new ListSection(SectionType.ACHIEVEMENT, ResumeInfo.ACHIEVEMENTS);
        resume.addSection(achievement);
        Section qualification = new ListSection(SectionType.QUALIFICATIONS, ResumeInfo.QUALIFICATIONS);
        resume.addSection(qualification);
    }

    private static void addCompanySections(Resume resume) {
        resume.addSection(ResumeInfo.JAVA_ONLINE_PROJECTS);
        resume.addSection(ResumeInfo.WRIKE);
        resume.addSection(ResumeInfo.RIT);
        resume.addSection(ResumeInfo.LUXOFT);
        resume.addSection(ResumeInfo.YOTA);
        resume.addSection(ResumeInfo.ENKATA);
        resume.addSection(ResumeInfo.SIEMENS_AG);
        resume.addSection(ResumeInfo.ALCATEL);

        resume.addSection(ResumeInfo.COURSERA);
        resume.addSection(ResumeInfo.LUXOFT_EDU);
        resume.addSection(ResumeInfo.SIEMENS_EDU);
        resume.addSection(ResumeInfo.ALCATEL_EDU);
        resume.addSection(ResumeInfo.UNIVERSITY);
    }
}
