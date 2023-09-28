package servlet;

import com.google.gson.Gson;
import dto.primary.DTOWorldGridForUi;
import engine.per.file.engine.api.SystemEngineAccess;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class GridSizesServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String simulationName = request.getParameter("simulation_name");
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            SystemEngineAccess systemEngineAccess = ServletUtils.getSEInstanceBySimulationName
                    (getServletContext(), simulationName);
            DTOWorldGridForUi dtoWorldGridForUi = systemEngineAccess.getDTOWorldGridForUi();
            String json = gson.toJson(dtoWorldGridForUi);
            out.println(json);
            out.flush();
        }
    }
}
