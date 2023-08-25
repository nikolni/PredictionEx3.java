package ui.impl;

import dto.api.DTOEnvVarsDefForUi;
import dto.api.DTOSimulationEndingForUi;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.property.instance.api.PropertyInstanceDTO;
import system.engine.api.SystemEngineAccess;
import system.engine.world.definition.property.api.PropertyDefinition;
import ui.api.MenuExecution;
import ui.dto.creation.CreateDTOMenu3ForSE;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Menu3 implements MenuExecution {
    @Override
    public void executeUserChoice(SystemEngineAccess systemEngine) {
        executeSimulation(systemEngine);
    }

    public void executeSimulation(SystemEngineAccess systemEngineAccess){
        DTOEnvVarsDefForUi dtoEnvVarsDefForUi = systemEngineAccess.getEVDFromSE();
        List<Object> initValues = new ArrayList<>(Collections.nCopies(dtoEnvVarsDefForUi.getEnvironmentVars().size(), null));

        printEnvironmentVarsDataAndCollectValueFromUser(systemEngineAccess, dtoEnvVarsDefForUi, initValues);
        systemEngineAccess.updateEnvironmentVarDefinition(new CreateDTOMenu3ForSE().getDataForMenu3(initValues,
                dtoEnvVarsDefForUi.getEnvironmentVars()));
        printEnvironmentVarsDataAfterGeneration(systemEngineAccess.getEVIFromSE().getEnvironmentVars());
        systemEngineAccess.addWorldInstance();
        runSimulation(systemEngineAccess);
    }

    private void runSimulation(SystemEngineAccess systemEngineAccess){
        try{
            DTOSimulationEndingForUi dtoSimulationEndingForUi = systemEngineAccess.runSimulation();
            System.out.println("\nSimulation running is done without errors!\n" +
                    "Simulation ID: " + (dtoSimulationEndingForUi.getSimulationID()+1) + "\n" +
                    "Termination reason: " + dtoSimulationEndingForUi.getTerminationReason());
        }
        catch (Exception e){
            System.out.println("\nSimulation running is terminated as a result of unexpected errors!\n" +
                    "Error description: " + e.getMessage());
        }
    }

    private void printEnvironmentVarsDataAndCollectValueFromUser(SystemEngineAccess systemEngine,
                                                                 DTOEnvVarsDefForUi dtoMenu3, List<Object> initValues){
        int envVarChoice =1;

        while(envVarChoice != 0){
            int countEnvVar = 0;
            System.out.println("Here is all environment variables names." +
                    "\nChoose the one you would like to init (by his number)" +
                    "\nIf you don't want to initialize anyone, press '0'.\n");
            for (PropertyDefinitionDTO environmentVar : dtoMenu3.getEnvironmentVars()) {
                countEnvVar++;
                System.out.println("#" + countEnvVar + " environment variable name: " + environmentVar.getUniqueName());
            }

            boolean wrongInput;
            do {
                wrongInput = false;
                System.out.println("\nEnter the number of the environment variable you would like to init (by his number).");
                envVarChoice = collectNumberFromUser();
                if (envVarChoice < 0 | envVarChoice > (dtoMenu3.getEnvironmentVars().size())) {
                    wrongInput = true;
                    System.out.println("Wrong input! Try again.");
                }
            }
            while (wrongInput);
            if(envVarChoice != 0){
                PropertyDefinitionDTO environmentVar = dtoMenu3.getEnvironmentVars().get(envVarChoice - 1);
                printPropertyDataForInitialize(environmentVar, countEnvVar);
                Object valueFromUser = collectValueFromUserAndCheckValidity(environmentVar.getType());
                initValues.set(envVarChoice - 1, valueFromUser);
            }
        }
    }


    private void printPropertyDataForInitialize(PropertyDefinitionDTO environmentVar, int countEnvVar){
        System.out.println("   #" + countEnvVar + " name: " + environmentVar.getUniqueName());
        System.out.println("     " + " type: " + environmentVar.getType());
        System.out.println("     " + (environmentVar.doesHaveRange() ? " range: from " +
                environmentVar.getRange().get(0) + " to " + environmentVar.getRange().get(1) : " no range"));

    }
    private void printEnvironmentVarsDataAfterGeneration(List<PropertyInstanceDTO> envVars) {
        int countEnvVar = 0;

        System.out.println("\nHere is the list of all environment variables after generation:");
        for(PropertyInstanceDTO environmentVar : envVars){
            countEnvVar++;
            System.out.println("#" + countEnvVar + " name: " + environmentVar.getPropertyDefinition().getUniqueName());
            System.out.println("   " + "value: " + environmentVar.getValue().toString());
        }
    }


    private Object collectValueFromUserAndCheckValidity(String envVarType){

        boolean isInputValid;
        Object value = null;

        do {
            System.out.println("Enter value");
            isInputValid= true;
            String valueFromUser = collectValueFromUser();
            try {
                switch (envVarType) {
                    case "DECIMAL":
                        value = Integer.parseInt(valueFromUser);
                        break;
                    case "FLOAT":
                        value = Float.parseFloat(valueFromUser);
                        break;
                    case "STRING":
                        break;
                    case "BOOLEAN":
                        value = Boolean.parseBoolean(valueFromUser);
                        break;
                }
            }
            catch (NumberFormatException e){
                System.out.println("wrong value type! try again");
                isInputValid= false;
            }
        }
        while (!isInputValid);

        return value;
    }

    private String collectValueFromUser(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String userInput = reader.readLine();
            return userInput;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int collectNumberFromUser(){
        boolean isInputValid= true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;

        do{
            System.out.println("Enter your choice");
            isInputValid= true;

            try {
                String userInput = reader.readLine();
                num = Integer.parseInt(userInput);

            } catch (IOException e) {
                e.printStackTrace();
                isInputValid= false;
            }
            catch (NumberFormatException e){
                System.out.println("Only numbers! Try again.");
                isInputValid= false;
            }
        }
        while (!isInputValid);

        return num;
    }
}
