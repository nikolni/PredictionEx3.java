package servlet;

import com.google.gson.Gson;
import dto.include.DTOIncludeForExecutionForServer;
import dto.include.DTOIncludeSimulationDetailsForUi;
import dto.primary.*;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SimulationDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String simulationName = request.getParameter("simulation_name");

        SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName(
                getServletContext(), simulationName);

        DTODefinitionsForUi definitions = systemEngineAccess.getDefinitionsDataFromSE();
        DTOEnvVarsDefForUi envVarsDefForUi = systemEngineAccess.getEVDFromSE();
        DTOWorldGridForUi worldGridForUi = systemEngineAccess.getDTOWorldGridForUi();

        DTOIncludeSimulationDetailsForUi simulationDetailsForUi = new DTOIncludeSimulationDetailsForUi(
                definitions, envVarsDefForUi, worldGridForUi);
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(simulationDetailsForUi);
            out.println(json);
            out.flush();
        }
    }

}
