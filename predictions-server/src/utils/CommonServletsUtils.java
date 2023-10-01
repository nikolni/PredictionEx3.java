package utils;

import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServlet;
import user.request.UserRequest;

import java.util.List;
import java.util.Map;

public abstract class CommonServletsUtils extends HttpServlet {
    protected SystemEngineAccess getSEInstance(String userName, Integer executionID){
        Map<UserRequest, List<Integer>> userRequestListMap = ServletUtils.getRequestsMapByUserName(getServletContext(), userName);
        String simulationName = getSimulationNameOfExecution(executionID, userRequestListMap);
        return ServletUtils.getSEInstanceBySimulationName(getServletContext(), simulationName);
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
