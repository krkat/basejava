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
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size=30
                           value="${resume.getContact(contactType).trim()}">
                </dd>
            </dl>
        </c:forEach>

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <h3>${sectionType.title}</h3>
            <c:choose>
                <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                    <input type="text" name="${sectionType.name()}" size=30
                           value="${resume.getSection(sectionType)}">
                </c:when>
                <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)}">
                            <textarea id="listSection" name="${sectionType.name()}" rows="5"
                                      cols="30">${resume.getSection(sectionType)}</textarea>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
                    <input class="field-label" type="text"
                           placeholder="Название"
                           name="${sectionType.name()}" size=50
                           value>
                    <br>
                    <input class="field-label" type="text"
                           placeholder="Ссылка"
                           name="${sectionType.name()}" size=50
                           value>
                    <div>
                        <input class="field-label" type="text"
                               placeholder="Начало, ММ/ГГГГ"
                               name="${sectionType.name()}" size=10
                               value>
                        <input class="field-label" type="text"
                               placeholder="Окончание, ММ/ГГГГ"
                               name="${sectionType.name()}" size=10
                               value>
                    </div>
                    <input class="field-label" type="text"
                           placeholder="Заголовок"
                           name="${sectionType.name()}" size=50
                           value>
                    <br>
                    <textarea class="field-label"
                              placeholder="Описание"
                              name="${sectionType.name()}" rows="5"
                              cols="50"></textarea>
                    <br>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>

        <c:choose>
            <c:when test="<%=!resume.getSections().get(SectionType.EXPERIENCE).isEmpty()%>">
                <h3>Опыт работы</h3>
                <c:forEach var="section" items="<%=resume.getSections().get(SectionType.EXPERIENCE)%>">
                    <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section" scope="page"/>
                    <input class="field-label" type="text"
                           placeholder="Название"
                           size=50
                           value="<%=((CompanySection)section).getName()%>">
                    <br>
                    <input class="field-label" type="text"
                           placeholder="Ссылка"
                           size=50
                           value="<%=((CompanySection)section).getWebsite()%>">
                    <div>
                        <input class="field-label" type="text"
                               placeholder="Начало, ММ/ГГГГ"
                               size=10
                               value>
                        <input class="field-label" type="text"
                               placeholder="Окончание, ММ/ГГГГ"
                               size=10
                               value>
                    </div>
                    <input class="field-label" type="text"
                           placeholder="Заголовок"
                           size=50
                           value>
                    <br>
                    <textarea class="field-label"
                              placeholder="Описание"
                              rows="5"
                              cols="50"></textarea>
                    <br>
                    <c:forEach var="period" items="<%=((CompanySection) section).getPeriods()%>">
                        <jsp:useBean id="period" type="ru.javawebinar.basejava.model.Period" scope="page"/>
                        <div>
                            <input class="field-label" type="text"
                                   placeholder="Начало, ММ/ГГГГ"
                                   size=10
                                   value="<%=DateUtil.format(period.getStartDate())%>">
                            <input class="field-label" type="text"
                                   placeholder="Окончание, ММ/ГГГГ"
                                   size=10
                                   value="<%=DateUtil.format(period.getEndDate())%>">
                        </div>
                        <input class="field-label" type="text"
                               placeholder="Заголовок"
                               size=50
                               value<%=period.getPosition()%>>
                        <br>
                        <textarea class="field-label"
                                  placeholder="Описание"
                                  rows="5"
                                  cols="50"><%=period.getDescription()%></textarea>
                        <br>
                        <hr>
                    </c:forEach>
                </c:forEach>
            </c:when>
            </c:choose>

        <c:choose>
            <c:when test="<%=!resume.getSections().get(SectionType.EDUCATION).isEmpty()%>">
                <h3>Образование</h3>
                <c:forEach var="sectionE" items="<%=resume.getSections().get(SectionType.EDUCATION)%>">
                    <jsp:useBean id="sectionE" type="ru.javawebinar.basejava.model.Section" scope="page"/>
                    <input class="field-label" type="text"
                           placeholder="Название"
                           size=50
                           value="<%=((CompanySection)sectionE).getName()%>">
                    <br>
                    <input class="field-label" type="text"
                           placeholder="Ссылка"
                           size=50
                           value="<%=((CompanySection)sectionE).getWebsite()%>">
                    <div>
                        <input class="field-label" type="text"
                               placeholder="Начало, ММ/ГГГГ"
                               size=10
                               value>
                        <input class="field-label" type="text"
                               placeholder="Окончание, ММ/ГГГГ"
                               size=10
                               value>
                    </div>
                    <input class="field-label" type="text"
                           placeholder="Заголовок"
                           size=50
                           value>
                    <br>
                    <textarea class="field-label"
                              placeholder="Описание"
                              rows="5"
                              cols="50"></textarea>
                    <br>
                    <c:forEach var="periodE" items="<%=((CompanySection) sectionE).getPeriods()%>">
                        <jsp:useBean id="periodE" type="ru.javawebinar.basejava.model.Period" scope="page"/>
                        <div>
                            <input class="field-label" type="text"
                                   placeholder="Начало, ММ/ГГГГ"
                                   size=10
                                   value="<%=DateUtil.format(periodE.getStartDate())%>">
                            <input class="field-label" type="text"
                                   placeholder="Начало, ММ/ГГГГ"
                                   size=10
                                   value="<%=DateUtil.format(periodE.getEndDate())%>">
                        </div>
                        <input class="field-label" type="text"
                               placeholder="Заголовок"
                               size=50
                               value<%=periodE.getPosition()%>>
                        <br>
                        <textarea class="field-label"
                                  placeholder="Описание"
                                  rows="5"
                                  cols="50"><%=periodE.getDescription()%></textarea>
                        <br>
                        <hr>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>

        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>