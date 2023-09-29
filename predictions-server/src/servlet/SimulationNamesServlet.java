package servlet;

import com.google.gson.Gson;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SimulationNamesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            List<String> simulationNamesList = ServletUtils.getSimulationNamesList(getServletContext());
            String json = gson.toJson(simulationNamesList);
            out.println(json);
            out.flush();
        }
    }
}
