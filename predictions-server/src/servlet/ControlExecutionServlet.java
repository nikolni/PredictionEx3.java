package servlet;

import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CommonServletsUtils;
import utils.ServletUtils;

import java.io.IOException;
import java.util.Properties;


public class ControlExecutionServlet extends CommonServletsUtils {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {

        Properties prop = new Properties();
        prop.load(request.getInputStream());

        String userName = prop.getProperty("user_name");
        Integer executionID = Integer.parseInt(prop.getProperty("executionID"));
        String action = prop.getProperty("action");

        SystemEngineAccess systemEngineAccess = getSEInstance(userName, executionID);

        switch (action){
            case "pause":
                systemEngineAccess.pauseSimulation(executionID);
            case "resume":
                systemEngineAccess.resumeSimulation(executionID);
            case "stop":
                systemEngineAccess.cancelSimulation(executionID);
        }
    }

}