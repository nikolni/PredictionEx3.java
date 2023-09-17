package jaxb2.copy;


import jaxb2.error.handling.validator.FileValidator;
import jaxb2.error.handling.validator.PRDWorldValidator;
import jaxb2.generated.PRDWorld;
import system.engine.world.api.WorldDefinition;
import system.engine.world.grid.api.WorldGrid;
import system.engine.world.grid.impl.WorldGridImpl;
import system.engine.world.impl.WorldDefinitionImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class WorldFromXml {
    public WorldDefinition FromXmlToPRDWorld(String xmlPathName) throws JAXBException, FileNotFoundException {
        //check XML path
        FileValidator fileValidator = new FileValidator();
        fileValidator.validateXmlFile(xmlPathName);
        //here the xml path is valid and we create PRDWorld

        PRDWorld PrdWorld = deserializeFrom(xmlPathName);
        //validation of PrdWorld
        PRDWorldValidator prdWorldValidator=new PRDWorldValidator();
        prdWorldValidator.validatePRDWorld(PrdWorld);
        return createWorldDefinitionFromPRDWorld(PrdWorld);
    }



    private static PRDWorld deserializeFrom(String xmlPathName) throws JAXBException, FileNotFoundException {
        JAXBContext jc = JAXBContext.newInstance(PRDWorld.class);
        return (PRDWorld) jc.createUnmarshaller().unmarshal(new FileReader(xmlPathName));
    }



    WorldDefinition createWorldDefinitionFromPRDWorld(PRDWorld prdWorld){
        int threadPoolSize=prdWorld.getPRDThreadCount();
        WorldGrid worldGrid=new WorldGridImpl(prdWorld.getPRDGrid().getRows(),prdWorld.getPRDGrid().getColumns());
        EnvironmentVariableFromXML environmentVariableFromXML=new EnvironmentVariableFromXML(prdWorld.getPRDEnvironment());
        EntityFromXML entityFromXML=new EntityFromXML(prdWorld.getPRDEntities());
        RuleFromXML ruleFromXML=new RuleFromXML(prdWorld.getPRDRules(),entityFromXML.getEntityDefinitionManager(),worldGrid);
        TerminationFromXML terminationFromXML=new TerminationFromXML(prdWorld.getPRDTermination());


        WorldDefinition worldDefinition=new WorldDefinitionImpl(entityFromXML.getEntityDefinitionManager(),environmentVariableFromXML.getEnvVariablesDefinitionManager()
                ,ruleFromXML.getRuleDefinitionManager(),terminationFromXML.getTerminationConditionsManager(),worldGrid,threadPoolSize);
        return worldDefinition;
    }

}
