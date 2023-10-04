package servlet;

import com.google.gson.Gson;
import dto.include.DTOIncludeForExecutionForServer;
import dto.primary.DTOEnvVarDefValuesForSE;
import dto.primary.DTOPopulationValuesForSE;
import engine.per.file.engine.api.SystemEngineAccess;
import engine.per.file.engine.world.termination.condition.api.TerminationCondition;
import jakarta.servlet.http.HttpServlet;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

        List<TerminationCondition> terminationConditionsList = ServletUtils.getAllocationsManager(getServletContext()).
                getAllUsersRequestsList().get(requestID -1).getTerminationConditionList();

        SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName
                (getServletContext(), simulationName);
        Integer executionID = ServletUtils.getAllocationsManager(getServletContext()).getExecutionsCounter();
        ServletUtils.getAllocationsManager(getServletContext()).addExecutionByUserNameAndRequestID(userName, requestID);
        ServletUtils.getAllocationsManager(getServletContext()).increaseExecutionCounter();

        systemEngineAccess.prepareForExecution(dtoEnvVarDefValuesForSE, dtoPopulationValuesForSE, executionID);
        ServletUtils.getThreadPoolManager(getServletContext()).addTaskToQueue(() -> systemEngineAccess.runSimulation(executionID,
                terminationConditionsList));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            Integer executionID = (ServletUtils.getAllocationsManager(getServletContext()).getExecutionsCounter());
            String json = gson.toJson(executionID);
            out.println(json);
            out.flush();
        }
    }
}
