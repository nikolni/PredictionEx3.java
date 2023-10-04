package servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

public class SetThreadPoolSizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        int threadsPoolSize = Integer.parseInt(request.getParameter("size"));
        ServletUtils.getThreadPoolManager(getServletContext()).setSizeOfThreadPool(threadsPoolSize);
    }
}
