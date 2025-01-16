<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h1>Имя:</h1>
        <dl>
            <input type="text" name="fullName" size=55 value="${resume.fullName}" required>
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size=30
                           value="${resume.getContact(contactType).trim()}">
                </dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType" scope="page"/>
            <h2>${sectionType.title}</h2>
            <c:choose>
                <c:when test="${sectionType.equals(SectionType.OBJECTIVE)}">
                    <input type="text" name="${sectionType.name()}" size=75
                           value="${resume.sectionToString(sectionType)}">
                </c:when>
                <c:when test="${sectionType.equals(SectionType.PERSONAL)}">
                    <textarea name="${sectionType.name()}" cols=75 rows=5>${resume.sectionToString(sectionType)}</textarea>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)}">
                    <textarea id="listSection" name="${sectionType.name()}" cols=75
                              rows=5>${resume.sectionToString(sectionType)}</textarea>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
                    <c:forEach var="section" items="<%=resume.getSections().get(sectionType)%>" varStatus="counter">
                        <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section" scope="page"/>
                        <input class="field-label" type="text"
                               placeholder="Название"
                               name="${sectionType.name()}"
                               size=100
                               value="<%=((CompanySection)section).getName()%>">
                        <br>
                        <input class="field-label" type="text"
                               placeholder="Ссылка"
                               name="${sectionType.name()}url"
                               size=50
                               value="<%=((CompanySection)section).getWebsite()%>">
                        <br>
                        <c:forEach var="period" items="<%=((CompanySection) section).getPeriods()%>">
                            <jsp:useBean id="period" type="ru.javawebinar.basejava.model.Period" scope="page"/>
                            <div>
                                <input class="field-label" type="text"
                                       placeholder="Начало, ММ/ГГГГ"
                                       name="${sectionType.name()}${counter.index}startDate"
                                       size=10
                                       value="<%=DateUtil.format(period.getStartDate())%>">
                                <input class="field-label" type="text"
                                       placeholder="Окончание, ММ/ГГГГ"
                                       name="${sectionType.name()}${counter.index}endDate"
                                       size=10
                                       value="<%=DateUtil.format(period.getEndDate())%>">
                            </div>
                            <input class="field-label" type="text"
                                   placeholder="Позиция"
                                   name="${sectionType.name()}${counter.index}position"
                                   size=50
                                   value="<%=period.getPosition()%>">
                            <br>
                            <textarea class="field-label"
                                      placeholder="Описание"
                                      name="${sectionType.name()}${counter.index}description"
                                      rows="5"
                                      cols="50"><%=period.getDescription()%></textarea>
                            <br>
                            <hr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>