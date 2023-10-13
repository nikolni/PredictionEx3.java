package servlet.user;

import com.google.gson.Gson;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SimulationNamesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String[] simulationNamesList = (ServletUtils.getSimulationNamesList(getServletContext())).toArray(new String[0]);
            String json = gson.toJson(simulationNamesList);
            out.println(json);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        boolean inUserList;

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        String json = requestBody.toString();
        List<String> simulationsNamesFromUser = new ArrayList<>();
        simulationsNamesFromUser.addAll( Arrays.asList(new Gson().fromJson(json, String[].class)));
        List<String> simulationsNamesFromServer = ServletUtils.getSimulationNamesList(getServletContext());
        for(String nameFromServer: simulationsNamesFromServer){
            inUserList = false;
            for(String nameFromUser: simulationsNamesFromUser){
                if(nameFromServer.equals(nameFromUser)){
                    inUserList = true;
                    break;
                }
            }
            if(!inUserList){
                simulationsNamesFromUser.add(nameFromServer);
            }
        }

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String[] newSimulationNamesListForUser = simulationsNamesFromUser.toArray(new String[0]);
            json = gson.toJson(newSimulationNamesListForUser);
            out.println(json);
            out.flush();
        }
    }
}
