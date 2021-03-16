package net.adriantodt.tinyfun.server.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.adriantodt.tinyfun.manager.FunManager;
import net.adriantodt.tinyfun.utils.Util;

import java.io.IOException;

public class FunServlet extends HttpServlet {
    private final FunManager manager;

    public FunServlet(FunManager manager) {
        this.manager = manager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || !pathInfo.startsWith("/") || pathInfo.equals("/")) {
            throw new IllegalArgumentException("pathInfo");
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        try {
            manager.execute(
                Util.SLASH_PATTERN.split(pathInfo.substring(1)),
                request.getInputStream(),
                response.getOutputStream()
            );
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
