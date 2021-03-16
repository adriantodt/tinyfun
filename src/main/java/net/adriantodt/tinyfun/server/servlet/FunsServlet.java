package net.adriantodt.tinyfun.server.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.grack.nanojson.JsonWriter;
import net.adriantodt.tinyfun.manager.FunManager;

import java.io.IOException;

public class FunsServlet extends HttpServlet {
    private final FunManager manager;

    public FunsServlet(FunManager manager) {
        this.manager = manager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        JsonWriter.on(response.getWriter())
            .array(manager.loadFunctions().keySet())
            .done();
    }
}
