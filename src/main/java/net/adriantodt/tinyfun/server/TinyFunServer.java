package net.adriantodt.tinyfun.server;

import jakarta.servlet.Servlet;
import net.adriantodt.tinyfun.manager.FunManager;
import net.adriantodt.tinyfun.server.servlet.FunServlet;
import net.adriantodt.tinyfun.server.servlet.FunsServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.jetbrains.annotations.NotNull;

public class TinyFunServer {
    private static final int maxThreads = 100;
    private static final int minThreads = 10;
    private static final int idleTimeout = 120;

    public static void main(String[] args) throws Exception {
        var jetty = new Server(new QueuedThreadPool(maxThreads, minThreads, idleTimeout));
        jetty.setConnectors(createConnector(args, jetty));
        jetty.setHandler(createHandler());
        jetty.start();
    }

    @NotNull
    private static Connector[] createConnector(String[] args, Server jetty) {
        var connector = new ServerConnector(jetty);
        connector.setPort(args.length > 0 ? Integer.parseInt(args[0]) : 8000);
        return new Connector[]{connector};
    }

    @NotNull
    private static ServletHandler createHandler() {
        var handler = new ServletHandler();

        var manager = new FunManager();
        configurePath(handler, new FunsServlet(manager), "/funs");
        configurePath(handler, new FunServlet(manager), "/fun/*");

        return handler;
    }

    private static void configurePath(ServletHandler handler, Servlet servlet, String path) {
        handler.addServletWithMapping(new ServletHolder(servlet), path);
    }
}
