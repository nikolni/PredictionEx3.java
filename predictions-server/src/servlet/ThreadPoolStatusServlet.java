package servlet;

import com.google.gson.Gson;
import dto.primary.DTOThreadsPoolStatusForUi;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class ThreadPoolStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        DTOThreadsPoolStatusForUi dtoThreadsPoolStatus = ServletUtils.getThreadPoolManager(getServletContext()).getThreadsPoolStatus();

        Gson gson = new Gson();
        String json = gson.toJson(dtoThreadsPoolStatus);

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

            out.println(json);
            out.flush();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        int threadsPoolSize = Integer.parseInt(request.getHeader("size"));
        ServletUtils.getThreadPoolManager(getServletContext()).setSizeOfThreadPool(threadsPoolSize);
    }
}