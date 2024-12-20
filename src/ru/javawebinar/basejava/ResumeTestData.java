package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        ResumeInfo.addContacts(resume);
        ResumeInfo.addTextSections(resume);
        ResumeInfo.addListSections(resume);
        ResumeInfo.addCompanySections(resume);

        resume.print();
    }
}