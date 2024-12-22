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
        StringBuilder builder = new StringBuilder("<head><title>Резюме</title></head>" +
                "<body><table>\n" +
                "    <thead>\n" +
                "        <th>Uuid</th>\n" +
                "        <th>FullName</th>\n" +
                "    </thead>\n" +
                "    <tbody>\n");
        for (Resume resume : storage.getAllSorted()) {
            builder.append("    <tr>\n" +
                    "        <td>" + resume.getUuid() + "</td>\n" +
                    "        <td>" + resume.getFullName() + "</td>\n" +
                    "    </tr>\n"
            );
        }
        builder.append("</tbody>\n" +
        "</table></body>");
        resp.getWriter().write(builder.toString());
    }
}
