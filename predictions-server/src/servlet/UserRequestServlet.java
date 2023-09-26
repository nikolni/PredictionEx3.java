package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.request.UserRequest;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;

public class UserRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String simulationName = request.getParameter("simulation name");
        Integer numberOfExecutions = Integer.parseInt(request.getParameter("number of executions"));
        String terminationConditions = request.getParameter("termination conditions");

        Gson gson = new Gson();

        UserRequest userRequest = new UserRequest(simulationName,numberOfExecutions,terminationConditions);
        List<UserRequest> userRequestList = ServletUtils.getUserRequestList(getServletContext());

        synchronized (this){userRequestList.add(userRequest);}

        // response header content-type can hint the client initiating this request regarding the nature of the response...
        response.setContentType("text/plain");
        response.getWriter().println("Your request is being processed");
    }
}