package servlet;

import com.google.gson.Gson;
import engine.per.file.engine.api.SystemEngineAccess;
import user.request.UserRequest;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserExecutionsListUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<Integer, String> simulationIdToStatus = new HashMap<>();

        String userName = request.getHeader("user_name");

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        String jsonString = requestBody.toString();
        Integer[] executionsIdList = new Gson().fromJson(jsonString, Integer[].class);
        Map<UserRequest, List<Integer>> userRequestListMap = ServletUtils.getRequestsMapByUserName(getServletContext(), userName);
        for(Integer executionID : executionsIdList){
            String simulationName = getSimulationNameOfExecution(executionID, userRequestListMap);
            SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName
                    (getServletContext(), simulationName);
            simulationIdToStatus.put(executionID, systemEngineAccess.getSimulationStatusByID(executionID));
        }
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(simulationIdToStatus);
            out.println(json);
            out.flush();
        }
    }
    private String getSimulationNameOfExecution(Integer executionId, Map<UserRequest, List<Integer>> userRequestListMap){
        for(UserRequest userRequest : userRequestListMap.keySet()){
            for(Integer id : userRequestListMap.get(userRequest)){
                if(id.equals(executionId)){
                    return userRequest.getSimulationName();
                }
            }
        }
        return null;
    }
}