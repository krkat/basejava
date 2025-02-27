<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/resume-list-styles.css">
    <title>Список всех резюме</title>
</head>
<body>
<div class="themes">
    <div class="theme-title">Тема</div>
    <div class="theme-selector">
        <form action="" method="GET">
            <select name="theme" onchange="this.form.submit()">
                <option value="light" ${theme == null || theme == 'light' ? 'selected' : ''}>Светлая</option>
                <option value="dark" ${theme == 'dark' ? 'selected' : ''}>Темная</option>
                <option value="purple" ${theme == 'purple' ? 'selected' : ''}>Фиолетовая</option>
                <option value="dark_green" ${theme == 'dark_green' ? 'selected' : ''}>Темно-зеленая</option>
            </select>
        </form>
    </div>
</div>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
    <div class="table-wrapper">
        <div class="add-resume">
            <a class="no-underline-anchor" href="resume?action=create&theme=${theme}">
                <img src="img/${theme}/add-person.png" alt="">
            </a>
            <a class="text-anchor" href="resume?action=create&theme=${theme}">
                <p class="add-resume-title">Добавить резюме</p>
            </a>
        </div>
        <div class="resumes-list">
            <table>
                <tr class="t-header">
                    <th class="head-name-column">Имя</th>
                    <th class="head-name-column">Контакты</th>
                    <th class="head-name-column">Редактировать</th>
                    <th class="head-name-column">Удалить</th>
                </tr>
                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                    <tr class="t-body">
                        <td class="name-column">
                            <a class="contact-link"
                               href="resume?uuid=${resume.uuid}&action=view&theme=${theme}">${resume.fullName}</a>
                        </td>
                        <td class="info-column">
                            <%=ContactType.EMAIL.toLink(resume.getContact(ContactType.EMAIL))%>
                        </td>
                        <td class="img-column">
                            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&action=edit&theme=${theme}">
                                <img src="img/${theme}/edit.png">
                            </a>
                        </td>
                        <td class="img-column">
                            <a class="no-underline-anchor"
                               href="resume?uuid=${resume.uuid}&action=delete&theme=${theme}">
                                <img src="img/${theme}/remove.png">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>