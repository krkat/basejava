package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.CompanySection;
import ru.javawebinar.basejava.model.Period;
import ru.javawebinar.basejava.model.Section;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeInfo {
    static final String PHONE = "+7(921) 855-0482";
    static final String PHONE_2 = "+7(921) 855-4444";
    static final String SKYPE = "skype:grigory.kislin";
    static final String EMAIL = "gkislin@yandex.ru";
    static final String LINKEDIN = "https://www.linkedin.com/in/gkislin";
    static final String GITHUB = "https://github.com/gkislin";
    static final String STACKOVERFLOW = "https://stackoverflow.com/users/548473";
    static final String HOME_PAGE = "http://gkislin.ru/";

    static final String OBJECTIVE = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
    static final String PERSONAL = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";

    static final List<String> ACHIEVEMENTS = new ArrayList<>(Arrays.asList("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
            "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
            "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
            "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
            "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
            "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
            "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
    ));

    static final List<String> QUALIFICATIONS = new ArrayList<>(Arrays.asList(
            "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
            "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB",
            "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
            "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
            "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
            "Python: Django.",
            "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
            "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
            "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
            "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
            "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
            "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
            "Родной русский, английский \"upper intermediate\""
    ));

    static final Period periodJavaOps = new Period(LocalDate.of(2013, 10, 1),
            LocalDate.now(),
            "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
    static final Period periodWrike = new Period(LocalDate.of(2014, 10, 1),
            LocalDate.of(2016, 1, 1),
            "Старший разработчик (backend)",
            "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
    static final Period periodRIT = new Period(LocalDate.of(2012, 4, 1),
            LocalDate.of(2014, 10, 1),
            "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
    static final Period periodLuxsoft = new Period(LocalDate.of(2010, 12, 1),
            LocalDate.of(2012, 4, 1),
            "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
    static final Period periodYota = new Period(LocalDate.of(2008, 6, 1),
            LocalDate.of(2010, 12, 1),
            "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
    static final Period periodEnkata = new Period(LocalDate.of(2007, 3, 1),
            LocalDate.of(2008, 6, 1),
            "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
    static final Period periodSiemens = new Period(LocalDate.of(2005, 1, 1),
            LocalDate.of(2007, 2, 1),
            "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
    static final Period periodAlcatel = new Period(LocalDate.of(1997, 9, 1),
            LocalDate.of(2005, 1, 1),
            "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");

    static final Section JAVA_ONLINE_PROJECTS = new CompanySection("Java Online Projects", "http://javaops.ru/", List.of(periodJavaOps));
    static final Section WRIKE = new CompanySection("Wrike", "https://www.wrike.com/", List.of(periodWrike));
    static final Section RIT = new CompanySection("RIT Center", "", List.of(periodRIT));
    static final Section LUXOFT = new CompanySection( "Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", List.of(periodLuxsoft));
    static final Section YOTA = new CompanySection("Yota", "https://www.yota.ru/", List.of(periodYota));
    static final Section ENKATA = new CompanySection( "Enkata", "http://enkata.com/", List.of(periodEnkata));
    static final Section SIEMENS_AG = new CompanySection("Siemens AG", "https://www.siemens.com/ru/ru/home.html", List.of(periodSiemens));
    static final Section ALCATEL = new CompanySection("Alcatel", "http://www.alcatel.ru/", List.of(periodAlcatel));

    static final Period periodCourseraEdu = new Period(LocalDate.of(2013, 3, 1),
            LocalDate.of(2013, 5, 1),
            "Студент", "'Functional Programming Principles in Scala' by Martin Odersky");
    static final Period periodLuxoftEdu = new Period(LocalDate.of(2011, 3, 1),
            LocalDate.of(2011, 4, 1),
            "Студент", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
    static final Period periodSiemensEdu = new Period(LocalDate.of(2005, 1, 1),
            LocalDate.of(2005, 4, 1),
            "Студент", "3 месяца обучения мобильным IN сетям (Берлин)");
    static final Period periodAlcatelEdu = new Period(LocalDate.of(1997, 9, 1),
            LocalDate.of(1998, 3, 1),
            "Студент", "6 месяцев обучения цифровым телефонным сетям (Москва)");
    static final Period periodPostgraduate = new Period(LocalDate.of(1993, 9, 1),
            LocalDate.of(1996, 7, 1),
            "Студент", "Аспирантура (программист С, С++)");
    static final Period periodStudent = new Period(LocalDate.of(1987, 9, 1),
            LocalDate.of(1993, 7, 1),
            "Студент", "Инженер (программист Fortran, C)");

    static final Section COURSERA = new CompanySection("Coursera", "https://www.coursera.org/course/progfun", List.of(periodCourseraEdu));
    static final Section LUXOFT_EDU = new CompanySection("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", List.of(periodLuxoftEdu));
    static final Section SIEMENS_EDU = new CompanySection("Siemens AG", "http://www.siemens.ru/", List.of(periodSiemensEdu));
    static final Section ALCATEL_EDU = new CompanySection("Alcatel", "http://www.alcatel.ru/", List.of(periodAlcatelEdu));
    static final Section UNIVERSITY = new CompanySection("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", List.of(periodPostgraduate, periodStudent));
}
