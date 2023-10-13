package allocation.manager;

import allocation.request.UserRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllocationsManager {
     private static List<UserRequest> allUsersRequestsList = null;
    private Map<String, Map<UserRequest, List<Integer>>> mapOfUserNameToRequestsMap = null;
    private int executionsCounter = 1;
    private int requestsCounter = 1;

    private static final Object allUsersRequestsListLock = new Object();
    private static final Object userNameToRequestsMapLock = new Object();
    private static final Object executionsCounterLock = new Object();
    private static final Object requestsCounterLock = new Object();

    public List<UserRequest> getAllUsersRequestsList() {
        return allUsersRequestsList;
    }

    public Map<String, Map<UserRequest, List<Integer>>> getMapOfUserNameToRequests(){
        return mapOfUserNameToRequestsMap;
    }

    public void addRequest(UserRequest userRequest) {
        synchronized (allUsersRequestsListLock) {
            if (allUsersRequestsList == null) {
                allUsersRequestsList = new ArrayList<>();
            }
            allUsersRequestsList.add(userRequest);
        }
        synchronized (requestsCounterLock) {
            requestsCounter++;
        }
    }
    public void addRequestByUserName(String userName ,UserRequest userRequest) {
        Map<UserRequest, List<Integer>> userRequestListMap;

        synchronized (userNameToRequestsMapLock) {
            if (mapOfUserNameToRequestsMap == null) {
                mapOfUserNameToRequestsMap = new HashMap<>();
            }
            if(mapOfUserNameToRequestsMap.get(userName) == null){
                userRequestListMap = new HashMap<>();
                mapOfUserNameToRequestsMap.put(userName,userRequestListMap);
            }
            userRequestListMap = mapOfUserNameToRequestsMap.get(userName);
            userRequestListMap.put(userRequest, null);
        }
    }

    public Map<UserRequest, List<Integer>> getRequestsMapByUserName(String userName) {
        Map<UserRequest, List<Integer>> userRequestListMap;

        synchronized (userNameToRequestsMapLock) {
            if (mapOfUserNameToRequestsMap == null) {
                mapOfUserNameToRequestsMap = new HashMap<>();
            }
            if(mapOfUserNameToRequestsMap.get(userName) == null){
                userRequestListMap = new HashMap<>();
                mapOfUserNameToRequestsMap.put(userName,userRequestListMap);
            }
        }
        return mapOfUserNameToRequestsMap.get(userName);
    }

    public void addExecutionByUserNameAndRequestID(String userName ,Integer requestID) {
        List<Integer> userRequestList;

        Map<UserRequest, List<Integer>> userRequestListMap = mapOfUserNameToRequestsMap.get(userName);

        synchronized (userNameToRequestsMapLock) {
            UserRequest userRequest = allUsersRequestsList.get(requestID -1);
            if(userRequestListMap.get(userRequest) == null){
                userRequestList = new ArrayList<>();
                userRequestListMap.put(userRequest,userRequestList);
            }
            userRequestListMap.get(userRequest).add(executionsCounter);
        }
    }

    public int getExecutionsCounter() {
        return executionsCounter;
    }
    public int getRequestsCounter() {
        return requestsCounter;
    }
    public void increaseExecutionCounter() {
        synchronized (executionsCounterLock) {
           executionsCounter++;
        }
    }
    public static void increaseNumOfSimulationsDone(Integer requestId) {
        allUsersRequestsList.get(requestId-1).increaseNumOfSimulationsDone();
    }
    public static void increaseNumOfSimulationsRunning(Integer requestId) {
        allUsersRequestsList.get(requestId-1).increaseNumOfSimulationsRunning();
    }
    public static void decreaseNumOfSimulationsRunning(Integer requestId) {
        allUsersRequestsList.get(requestId-1).decreaseNumOfSimulationsRunning();
    }

    public static void setRequestStatus(Integer requestId, String requestStatus) {
        allUsersRequestsList.get(requestId-1).setRequestStatus(requestStatus);
    }
}
