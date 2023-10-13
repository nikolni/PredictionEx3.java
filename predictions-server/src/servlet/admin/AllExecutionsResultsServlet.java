package servlet.admin;

import allocation.request.UserRequest;
import com.google.gson.Gson;
import dto.primary.DTOSimulationEndingForUi;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AllExecutionsResultsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, DTOSimulationEndingForUi> executionsDoneFromServer = new HashMap<>();

        Map<String, Map<UserRequest, List<Integer>>> userNameToRequestsMap =
                ServletUtils.getAllocationsManager(getServletContext()).getMapOfUserNameToRequests();

        if (userNameToRequestsMap != null) {
            for (String userName : userNameToRequestsMap.keySet()) {
                if (userNameToRequestsMap.get(userName) != null) {
                    for (UserRequest userRequest : userNameToRequestsMap.get(userName).keySet()) {

                        String simulationName = userRequest.getSimulationName();
                        SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName(getServletContext(), simulationName);
                        Collection<DTOSimulationEndingForUi> executionsDone = systemEngineAccess.getExecutionsDone();

                        if (userNameToRequestsMap.get(userName).get(userRequest) != null) {
                            for (Integer executionID : userNameToRequestsMap.get(userName).get(userRequest)) {
                                for (DTOSimulationEndingForUi dtoSimulationEndingForUi : executionsDone) {
                                    if (executionID == dtoSimulationEndingForUi.getSimulationID()) {
                                        String strToAdd = "Execution ID: " + executionID + " (user: " + userName + ", requestID: " +
                                                userRequest.getRequestID() + ")";
                                        executionsDoneFromServer.put(strToAdd, dtoSimulationEndingForUi);
                                    }
                                }
                            }
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