package after.login.component.body.simulation.details.main;


import after.login.component.body.simulation.details.server.RequestsFromServer;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class UpdateSimulationsTreeView implements Runnable{
    private List<String> simulationNamesList;
    private Integer simulationsNumber = 0;
    private final RequestsFromServer requestsFromServer;
    private final SimulationsDetailsController simulationsDetailsController;

    public UpdateSimulationsTreeView(SimulationsDetailsController simulationsDetailsController,
                                     RequestsFromServer requestsFromServer) {
        this.simulationsDetailsController = simulationsDetailsController;
        this.simulationNamesList = new ArrayList<>();

        this.requestsFromServer = requestsFromServer;
        requestsFromServer.setNewSimulationsNamesConsumer(this::useSimulationsNames);
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            requestsFromServer.getNewSimulationsNames(simulationNamesList.toArray(new String[0]));

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

    private void useSimulationsNames(List<String> simulationNamesConsumer){
        simulationNamesList = simulationNamesConsumer;
    }

}