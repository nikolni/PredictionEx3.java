package servlet.user;

import com.google.gson.Gson;
import dto.include.DTOIncludeForResultsAdditionalForUi;
import dto.include.DTOIncludeForResultsPrimaryForUi;
import dto.primary.*;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CommonServletsUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class ExecutionResultsServlet extends CommonServletsUtils {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("user_name");
        Integer executionID = Integer.parseInt(request.getParameter("executionID"));

        SystemEngineAccess systemEngineAccess = getSEInstance(userName, executionID);

        DTOEntitiesAfterSimulationByQuantityForUi dtoEntitiesAfterSimulationByQuantity =
                systemEngineAccess.getEntitiesDataAfterSimulationRunningByQuantity(executionID);
        DTODefinitionsForUi dtoDefinitions = systemEngineAccess.getDefinitionsDataFromSE();

        DTOIncludeForResultsPrimaryForUi dtoIncludeForResultsPrimaryForUi = new DTOIncludeForResultsPrimaryForUi(
                dtoEntitiesAfterSimulationByQuantity, dtoDefinitions);
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(dtoIncludeForResultsPrimaryForUi);
            out.println(json);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Properties prop = new Properties();
        prop.load(request.getInputStream());

        String userName = prop.getProperty("user_name");
        Integer executionID = Integer.parseInt(prop.getProperty("executionID"));
        String entityName = prop.getProperty("entity_name");
        String propertyName = prop.getProperty("property_name");

        SystemEngineAccess systemEngineAccess = getSEInstance(userName, executionID);


        DTOSimulationProgressForUi dtoSimulationProgress = systemEngineAccess.getDtoSimulationProgressForUi(executionID);
        DTOEntityPropertyConsistencyForUi dtoEntityPropertyConsistency = systemEngineAccess.getConsistencyDTOByEntityPropertyName(
                executionID, entityName, propertyName);
        DTOPropertyHistogramForUi dtoPropertyHistogram = systemEngineAccess.getPropertyDataAfterSimulationRunningByHistogramByNames(
                executionID, entityName, propertyName);

        DTOIncludeForResultsAdditionalForUi dtoIncludeForResultsAdditionalForUi = new DTOIncludeForResultsAdditionalForUi(
                dtoEntityPropertyConsistency, dtoSimulationProgress, dtoPropertyHistogram);
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(dtoIncludeForResultsAdditionalForUi);
            out.println(json);
            out.flush();
        }
    }
}