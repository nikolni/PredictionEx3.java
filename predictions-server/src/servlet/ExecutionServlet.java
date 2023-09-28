package servlet;

import com.google.gson.Gson;
import dto.include.DTOIncludeForExecutionForServer;
import dto.include.DTOIncludeForExecutionForUi;
import dto.primary.DTOEnvVarDefValuesForSE;
import dto.primary.DTOPopulationValuesForSE;
import engine.per.file.engine.api.SystemEngineAccess;
import user.request.UserRequest;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ExecutionServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        String simulationName = request.getParameter("simulation_name");
        String userName= request.getParameter("user_name");
        Integer requestID=Integer.parseInt(request.getParameter("requestID"));

        SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName
                (getServletContext(), simulationName);
        Integer executionID = ServletUtils.increaseExecutionCounter(getServletContext());
        ServletUtils.addExecutionByUserNameAndRequestID(getServletContext(), userName, requestID);

        systemEngineAccess.prepareForExecution(dtoEnvVarDefValuesForSE, dtoPopulationValuesForSE, executionID);
        ServletUtils.addRunnableToThreadPool(getServletContext(), new Runnable() {
            @Override
            public void run() {
                systemEngineAccess.runSimulation(executionID);
            }
        });
    }
}
