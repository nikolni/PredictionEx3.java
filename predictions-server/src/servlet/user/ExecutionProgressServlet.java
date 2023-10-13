package servlet.user;

import com.google.gson.Gson;
import dto.primary.DTOSimulationProgressForUi;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CommonServletsUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class ExecutionProgressServlet extends CommonServletsUtils {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("user_name");
        Integer executionID = Integer.parseInt(request.getParameter("executionID"));

        SystemEngineAccess systemEngineAccess = getSEInstance(userName, executionID);
        DTOSimulationProgressForUi dtoSimulationProgressForUi = systemEngineAccess.getDtoSimulationProgressForUi(executionID);
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(dtoSimulationProgressForUi);
            out.println(json);
            out.flush();
        }
    }
}