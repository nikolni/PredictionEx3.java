package after.login.component.body.simulation.details.main;

import after.login.component.body.request.server.RequestsFromServer;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class UpdateSimulationsTreeView implements Runnable{
    private List<String> simulationNamesList;
    private Integer simulationsNumber = 0;
    private final RequestsFromServer requestsFromServer = new RequestsFromServer();
    private final SimulationsDetailsController simulationsDetailsController;

    public UpdateSimulationsTreeView(SimulationsDetailsController simulationsDetailsController) {
        this.simulationsDetailsController = simulationsDetailsController;
        this.simulationNamesList = new ArrayList<>();
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            simulationNamesList = requestsFromServer.getSimulationNamesFromServer();
            if(simulationsNumber < simulationNamesList.size()){
                for(int i = simulationsNumber; i < simulationNamesList.size(); i++){
                    simulationsDetailsController.addSimulationItemToTreeView(simulationNamesList.get(i));
                }
                simulationsNumber = simulationNamesList.size();
            }
            try {
                sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}