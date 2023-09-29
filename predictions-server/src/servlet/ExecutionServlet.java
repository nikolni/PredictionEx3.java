package servlet;

import com.google.gson.Gson;
import dto.include.DTOIncludeForExecutionForServer;
import dto.primary.DTOEnvVarDefValuesForSE;
import dto.primary.DTOPopulationValuesForSE;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServlet;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExecutionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        String json = requestBody.toString();
        DTOIncludeForExecutionForServer dtoIncludeForExecutionForServer = new Gson().fromJson(json, DTOIncludeForExecutionForServer.class);
        DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE = dtoIncludeForExecutionForServer.getDtoEnvVarDefValuesForSE();
        DTOPopulationValuesForSE dtoPopulationValuesForSE = dtoIncludeForExecutionForServer.getDtoPopulationValuesForSE();

        String simulationName = request.getHeader("simulation_name");
        String userName= request.getHeader("user_name");
        Integer requestID=Integer.parseInt(request.getHeader("requestID"));

        SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName
                (getServletContext(), simulationName);
        Integer executionID = ServletUtils.getExecutionCounter(getServletContext());
        ServletUtils.addExecutionByUserNameAndRequestID(getServletContext(), userName, requestID);
        ServletUtils.increaseExecutionCounter(getServletContext());

        systemEngineAccess.prepareForExecution(dtoEnvVarDefValuesForSE, dtoPopulationValuesForSE, executionID);
        ServletUtils.addRunnableToThreadPool(getServletContext(), () -> systemEngineAccess.runSimulation(executionID));
    }
}
