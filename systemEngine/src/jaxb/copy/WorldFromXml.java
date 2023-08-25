package jaxb.copy;


import jaxb.error.handling.validator.FileValidator;
import jaxb.error.handling.validator.PRDWorldValidator;
import jaxb.generated.PRDWorld;
import system.engine.world.api.WorldDefinition;
import system.engine.world.impl.WorldDefinitionImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.*;

public class WorldFromXml {

    //private final static String JAXB_XML_GAME_PACKAGE_NAME = "jaxb.generator";


    public WorldDefinition FromXmlToPRDWorld(String xmlPathName) throws JAXBException,FileNotFoundException{
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



    WorldDefinition createWorldDefinitionFromPRDWorld(PRDWorld PrdWorld){
        EnvironmentVariableFromXML environmentVariableFromXML=new EnvironmentVariableFromXML(PrdWorld.getPRDEvironment());
        EntityFromXML entityFromXML=new EntityFromXML(PrdWorld.getPRDEntities());
        RuleFromXML ruleFromXML=new RuleFromXML(PrdWorld.getPRDRules(),entityFromXML.getEntityDefinitionManager());
        TerminationFromXML terminationFromXML=new TerminationFromXML(PrdWorld.getPRDTermination());

        WorldDefinition worldDefinition=new WorldDefinitionImpl(entityFromXML.getEntityDefinitionManager(),environmentVariableFromXML.getEnvVariablesDefinitionManager(),ruleFromXML.getRuleDefinitionManager(),terminationFromXML.getTerminationConditionsManager());
        return worldDefinition;
    }



}
