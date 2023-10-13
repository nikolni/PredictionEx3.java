package servlet.user;

import com.google.gson.Gson;
import dto.primary.DTOSimulationEndingForUi;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import allocation.request.UserRequest;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ExecutionEndingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<DTOSimulationEndingForUi> executionsDoneFromServer = new ArrayList<>();

        String userName = request.getParameter("user_name");

        Map<UserRequest, List<Integer>> userExecutions = ServletUtils.getAllocationsManager(getServletContext()).getRequestsMapByUserName(userName);

        for(UserRequest userRequest : userExecutions.keySet()){
            String simulationName = userRequest.getSimulationName();
            SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName(getServletContext(), simulationName);
            Collection<DTOSimulationEndingForUi> executionsDone = systemEngineAccess.getExecutionsDone();

            if(userExecutions.get(userRequest) != null) {
                for (Integer executionID : userExecutions.get(userRequest)) {
                    for (DTOSimulationEndingForUi dtoSimulationEndingForUi : executionsDone) {
                        if (executionID == dtoSimulationEndingForUi.getSimulationID()) {
                            executionsDoneFromServer.add(dtoSimulationEndingForUi);
                        }
                    }
                }
            }
        }
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(executionsDoneFromServer);
            out.println(json);
            out.flush();
        }
    }
}