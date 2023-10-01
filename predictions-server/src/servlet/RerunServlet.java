package servlet;

import com.google.gson.Gson;
import dto.primary.DTORerunValuesForUi;
import engine.per.file.engine.api.SystemEngineAccess;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RerunServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        String simulationName = request.getParameter("simulation_name");
        String executionID = request.getParameter("executionID");
        SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName
                (getServletContext(), simulationName);
        DTORerunValuesForUi dtoRerunValuesForUi= systemEngineAccess.getValuesForRerun(Integer.parseInt(executionID));

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(dtoRerunValuesForUi);
            out.println(json);
            out.flush();
        }
    }
}