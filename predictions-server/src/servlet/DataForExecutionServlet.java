package servlet;

import com.google.gson.Gson;
import dto.include.DTOIncludeForExecutionForUi;
import dto.primary.DTOEnvVarsDefForUi;
import dto.primary.DTONamesListForUi;
import dto.primary.DTOWorldGridForUi;
import engine.per.file.engine.api.SystemEngineAccess;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DataForExecutionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String simulationName = request.getParameter("simulation_name");
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName
                    (getServletContext(), simulationName);
            DTOWorldGridForUi dtoWorldGridForUi = systemEngineAccess.getDTOWorldGridForUi();
            DTOEnvVarsDefForUi dtoEnvVarsDefForUi= systemEngineAccess.getEVDFromSE();
            DTONamesListForUi dtoNamesListForUi = systemEngineAccess.getEntitiesNames();
            DTOIncludeForExecutionForUi dtoIncludeForExecutionForUi = new DTOIncludeForExecutionForUi(
                    dtoWorldGridForUi, dtoEnvVarsDefForUi, dtoNamesListForUi);
            String json = gson.toJson(dtoIncludeForExecutionForUi);
            out.println(json);
            out.flush();
        }
    }
}
