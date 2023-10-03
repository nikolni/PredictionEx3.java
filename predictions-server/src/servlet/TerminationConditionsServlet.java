package servlet;

import com.google.gson.Gson;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CommonServletsUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class TerminationConditionsServlet extends CommonServletsUtils {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("user_name");
        Integer executionID = Integer.parseInt(request.getParameter("executionID"));

        SystemEngineAccess systemEngineAccess = getSEInstance(userName, executionID);
        TerminationConditionsDTO[] terminationConditionsDTOList= systemEngineAccess.
                getTerminationConditions(executionID).toArray(new TerminationConditionsDTO[0]);
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(terminationConditionsDTOList);
            out.println(json);
            out.flush();
        }
    }
}
