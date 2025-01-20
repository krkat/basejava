<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/view-resume-styles.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
    <div class="form-wrapper">
        <div class="full-name">${resume.fullName}
            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&action=edit&theme=${theme}">
                <img src="img/${theme}/edit.png" alt="">
            </a>
        </div>
        <div class="contacts">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <div>
                    <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
                </div>
            </c:forEach>
        </div>

        <div class="spacer"></div>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, java.util.List<ru.javawebinar.basejava.model.Section>>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="sectionList" value="${sectionEntry.value}"/>
            <jsp:useBean id="sectionList"
                         type="java.util.List<ru.javawebinar.basejava.model.Section>"/>
            <div class="section">${type.title}</div>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <div class="position"><%=((TextSection) sectionList.get(0)).getContent()%>
                    </div>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <div class="qualities"><%=((TextSection) sectionList.get(0)).getContent()%>
                    </div>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <ul class="list">
                        <c:forEach var="item" items="<%=((ListSection) sectionList.get(0)).getItems()%>">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="company" items="<%=sectionList%>">
                        <jsp:useBean id="company" type="ru.javawebinar.basejava.model.Section"/>
                        <div class="section-wrapper">
                            <c:choose>
                                <c:when test="<%=((CompanySection) company).getWebsite().isEmpty()%>">
                                    <div class="job-name"><%=((CompanySection) company).getName()%>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="job-name">
                                        <a class="contact-link"
                                           href="<%=((CompanySection) company).getWebsite()%>">
                                            <%=((CompanySection) company).getName()%>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="period" items="<%=((CompanySection) company).getPeriods()%>">
                                <jsp:useBean id="period" type="ru.javawebinar.basejava.model.Period"/>
                                <div class="period-position">
                                    <div class="period"><%=HtmlHelper.formatDates(period)%>
                                    </div>
                                    <div class="position">${period.position}</div>
                                </div>
                                <c:choose>
                                    <c:when test="${empty period.description}"></c:when>
                                    <c:otherwise>
                                        <div class="description">${period.description}</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                        </div>
                    </c:forEach>

                </c:when>
            </c:choose>
        </c:forEach>

        <div class="footer-spacer"></div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>