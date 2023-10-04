package servlet;

import com.google.gson.Gson;
import dto.definition.user.request.DTOUserRequestForUi;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import allocation.request.UserRequest;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class getAllRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //returning JSON objects, not HTML
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            List<UserRequest> allAdminRequests = ServletUtils.getAllocationsManager(getServletContext()).getAllUsersRequestsList();
            List<DTOUserRequestForUi> dtoUserRequestForUiList=new ArrayList<>();
            DTOUserRequestForUi dtoUserRequestForUi;
            for(UserRequest userRequest:allAdminRequests){
                dtoUserRequestForUi=new DTOUserRequestForUi(userRequest.getRequestStatus(),
                        userRequest.getNumOfSimulationsRunning(), userRequest.getNumOfSimulationsDone(), userRequest.getRequestID(), userRequest.getSimulationName(),userRequest.getUserName(),
                        userRequest.getTerminationConditionListString(),userRequest.getNumOfSimulations());
                dtoUserRequestForUiList.add(dtoUserRequestForUi);
            }
            String json = gson.toJson(dtoUserRequestForUiList);
            out.println(json);
            out.flush();
        }
    }
}
