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
        resume.addContact(ContactType.PHONE, ResumeInfo.PHONE);
        resume.addContact(ContactType.SKYPE, ResumeInfo.SKYPE);
        resume.addContact(ContactType.EMAIL, ResumeInfo.EMAIL);
        resume.addContact(ContactType.LINKEDIN, ResumeInfo.LINKEDIN);
        resume.addContact(ContactType.GITHUB, ResumeInfo.GITHUB);
        resume.addContact(ContactType.STACKOVERFLOW, ResumeInfo.STACKOVERFLOW);
        resume.addContact(ContactType.HOME_PAGE, ResumeInfo.HOME_PAGE);
    }

    private static void addTextSections(Resume resume) {
        Section position = new TextSection(ResumeInfo.OBJECTIVE);
        resume.addSection(SectionType.OBJECTIVE, position);
        Section personal = new TextSection(ResumeInfo.PERSONAL);
        resume.addSection(SectionType.PERSONAL, personal);
    }

    private static void addListSections(Resume resume) {
        Section achievement = new ListSection(ResumeInfo.ACHIEVEMENTS);
        resume.addSection(SectionType.ACHIEVEMENT, achievement);
        Section qualification = new ListSection(ResumeInfo.QUALIFICATIONS);
        resume.addSection(SectionType.QUALIFICATIONS, qualification);
    }

    private static void addCompanySections(Resume resume) {
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.JAVA_ONLINE_PROJECTS);
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.WRIKE);
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.RIT);
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.LUXOFT);
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.YOTA);
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.ENKATA);
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.SIEMENS_AG);
        resume.addSection(SectionType.EXPERIENCE, ResumeInfo.ALCATEL);

        resume.addSection(SectionType.EDUCATION, ResumeInfo.COURSERA);
        resume.addSection(SectionType.EDUCATION,ResumeInfo.LUXOFT_EDU);
        resume.addSection(SectionType.EDUCATION,ResumeInfo.SIEMENS_EDU);
        resume.addSection(SectionType.EDUCATION,ResumeInfo.ALCATEL_EDU);
        resume.addSection(SectionType.EDUCATION,ResumeInfo.UNIVERSITY);
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        addContacts(resume);
        addTextSections(resume);
        addListSections(resume);
        addCompanySections(resume);
        return resume;
    }
}