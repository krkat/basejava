<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/edit-resume-styles.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <input type="hidden" name="theme" value="${theme}">
    <div class="scrollable-panel">
        <div class="form-wrapper">
            <div class="section">ФИО</div>
            <input class="field" type="text" name="fullName" size=55 placeholder="ФИО" value="${resume.fullName}"
                   required>
            <div class="section">Контакты</div>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <input class="field" type="text" name="${type.name()}" size=30 placeholder="${type.title}"
                       value="${resume.getContact(type)}">
            </c:forEach>
            <div class="spacer"></div>
            <div class="section">Секции</div>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType"/>
                <c:set var="sectionList" value="${resume.getSections(type)}"/>
                <jsp:useBean id="sectionList" type="java.util.List<ru.javawebinar.basejava.model.Section>"/>
                <div class="field-label">${type.title}</div>
                <c:choose>
                    <c:when test="${(type=='OBJECTIVE' || type=='PERSONAL')}">
                        <textarea class="field"
                                  name='${type}'><%=resume.sectionToString(type)%></textarea>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                        <textarea class="field"
                                  name='${type}'><%=resume.sectionToString(type)%></textarea>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="company" items="<%=sectionList%>"
                                   varStatus="counter">
                            <jsp:useBean id="company" type="ru.javawebinar.basejava.model.Section"/>
                            <c:choose>
                                <c:when test="${counter.index == 0}">
                                </c:when>
                                <c:otherwise>
                                    <div class="spacer"></div>
                                </c:otherwise>
                            </c:choose>
                            <%--                            <button class="green-button">Добавить</button>--%>
                            <input class="field" type="text" placeholder="Название" name='${type}' size=100
                                   value="<%=((CompanySection) company).getName()%>">
                            <input class="field" type="text" placeholder="Ссылка" name='${type}url' size=100
                                   value="<%=((CompanySection) company).getWebsite()%>">
                            <%--                            <button class="small-green-button">Добавить должность</button>--%>
                            <c:forEach var="period" items="<%=((CompanySection) company).getPeriods()%>">
                                <jsp:useBean id="period" type="ru.javawebinar.basejava.model.Period"/>
                                <div class="date-section">
                                    <input class="field date" name="${type}${counter.index}startDate"
                                           placeholder="Начало, ММ/ГГГГ"
                                           size=10
                                           value="<%=DateUtil.format(period.getStartDate())%>">
                                    <input class="field date date-margin" name="${type}${counter.index}endDate"
                                           placeholder="Окончание, ММ/ГГГГ"
                                           size=10
                                           value="<%=DateUtil.format(period.getEndDate())%>">
                                </div>
                                <input class="field" type="text" placeholder="Позиция"
                                       name='${type}${counter.index}position' size=75
                                       value="${period.position}">
                                <textarea class="field" placeholder="Описание"
                                          name="${type}${counter.index}description">${period.description}</textarea>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
            <div class="spacer"></div>
            <div class="button-section">
                <button class="red-cancel-button" onclick="window.history.back()">Отменить</button>
                <button class="green-submit-button" type="submit">Сохранить</button>
            </div>
        </div>
    </div>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>