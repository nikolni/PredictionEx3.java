package servlet;

import com.google.gson.Gson;
import dto.primary.DTOSecTicksForUi;
import dto.primary.DTOSimulationProgressForUi;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CommonServletsUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class SecTicksServlet extends CommonServletsUtils {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("user_name");
        Integer executionID = Integer.parseInt(request.getParameter("executionID"));

        SystemEngineAccess systemEngineAccess = getSEInstance(userName, executionID);
        Integer seconds = systemEngineAccess.getTotalSecondsNumber(executionID);
        Integer ticks = systemEngineAccess.getTotalTicksNumber(executionID);
        DTOSecTicksForUi dtoSecTicksForUi = new DTOSecTicksForUi(seconds, ticks);
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(dtoSecTicksForUi);
            out.println(json);
            out.flush();
        }
    }
}