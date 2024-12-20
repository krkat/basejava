package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        List<Resume> resumes = storage.getAllSorted();
        resp.getWriter().write("<head>Резюме</head>" +
                "<body><table>\n" +
                "    <thead>\n" +
                "        <th>Uuid</th>\n" +
                "        <th>FullName</th>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>" + resumes.get(1) + "</td>\n" +
                "        <td>Name1</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>2</td>\n" +
                "        <td>Name2</td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table></body>");
    }
}
