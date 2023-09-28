package servlet;

import com.google.gson.GsonBuilder;
import dto.definition.user.request.DTOUserRequestForUi;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.request.UserRequest;
import utils.ServletUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.gson.Gson;

public class UserRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, List<UserRequest>> userNameToRequestsList= ServletUtils.getMapUserNameToRequestsList(getServletContext());
        List<UserRequest> userRequestsList = userNameToRequestsList.get(request.getHeader("user_name"));

        List<DTOUserRequestForUi> userRequestForUiList = new ArrayList<>();
        for(UserRequest userRequest : userRequestsList){
            userRequestForUiList.add(new DTOUserRequestForUi(userRequest.getRequestStatus(),
                    userRequest.getNumOfSimulationsRunning(), userRequest.getNumOfSimulationsDone()));
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(userRequestForUiList);

        // Set the response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Properties prop = new Properties();
        prop.load(request.getInputStream());

        String simulationName = prop.getProperty("simulation name");
        Integer numberOfExecutions = Integer.parseInt(prop.getProperty("number of executions"));
        String terminationConditions = prop.getProperty("termination conditions");

        UserRequest userRequest = new UserRequest(simulationName,numberOfExecutions,terminationConditions);

        synchronized (this){
            List<UserRequest> allUsersRequestsList = ServletUtils.getUserRequestList(getServletContext());
            Map<String, List<UserRequest>> userNameToRequestsList= ServletUtils.getMapUserNameToRequestsList(getServletContext());
            allUsersRequestsList.add(userRequest);
            List<UserRequest> userRequestsList = userNameToRequestsList.get(request.getHeader("user name"));
            userRequestsList.add(userRequest);
        }
    }
}