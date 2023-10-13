package servlet.admin;

import allocation.request.UserRequest;
import com.google.gson.Gson;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CommonServletsUtils;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllExecutionsStatusesServlet extends CommonServletsUtils {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<Integer,String> simulationsStatuses = new HashMap<>();

        Map<String, Map<UserRequest, List<Integer>>> userNameToRequestsMap =
                ServletUtils.getAllocationsManager(getServletContext()).getMapOfUserNameToRequests();

        if (userNameToRequestsMap != null) {
            for (String userName : userNameToRequestsMap.keySet()) {
                if (userNameToRequestsMap.get(userName) != null) {
                    for (UserRequest userRequest : userNameToRequestsMap.get(userName).keySet()) {
                        if (userNameToRequestsMap.get(userName).get(userRequest) != null) {
                            for (Integer executionID : userNameToRequestsMap.get(userName).get(userRequest)) {
                                SystemEngineAccess systemEngineAccess = getSEInstance(userName, executionID);
                                simulationsStatuses.put(executionID, systemEngineAccess.getSimulationStatusByID(executionID));
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
            String json = gson.toJson(simulationsStatuses);
            out.println(json);
            out.flush();
        }
    }
}