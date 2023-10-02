package servlet;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dto.definition.user.request.DTOUserRequestForUi;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.request.UserRequest;
import utils.ServletUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.gson.Gson;

public class UserRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<UserRequest, List<Integer>> userRequestsMap = ServletUtils.getRequestsMapByUserName(
                getServletContext(), request.getParameter("user_name"));

        List<DTOUserRequestForUi> userRequestForUiList = new ArrayList<>();
        for(UserRequest userRequest : userRequestsMap.keySet()){
            userRequestForUiList.add(new DTOUserRequestForUi(userRequest.getRequestStatus(),
                    userRequest.getNumOfSimulationsRunning(), userRequest.getNumOfSimulationsDone(), userRequest.getRequestID(),userRequest.getSimulationName(),
                    userRequest.getUserName(),userRequest.getTerminationConditionListString(),userRequest.getNumOfSimulations()));
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(userRequestForUiList);

        // Set the response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Properties prop = new Properties();
        prop.load(request.getInputStream());

        String simulationName = prop.getProperty("simulation_name");
        Integer numberOfExecutions = Integer.parseInt(prop.getProperty("number_of_executions"));
        String terminationConditions = prop.getProperty("termination_conditions");

        UserRequest userRequest = new UserRequest(simulationName,numberOfExecutions,terminationConditions,request.getHeader("user name"));
        userRequest.setRequestID(ServletUtils.getAllUserRequestsListSize(getServletContext()));
        ServletUtils.addRequestToAllUsersRequestsList(getServletContext(), userRequest);
        ServletUtils.addRequestByUserName(getServletContext(), request.getHeader("user name"), userRequest);
    }
}