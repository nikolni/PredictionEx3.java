package jaxb.copy;

import jaxb.generated.PRDEnvProperty;
import jaxb.generated.PRDEvironment;
import system.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import system.engine.world.definition.environment.variable.impl.EnvVariableDefinitionManagerImpl;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.definition.property.impl.BooleanPropertyDefinition;
import system.engine.world.definition.property.impl.FloatPropertyDefinition;
import system.engine.world.definition.property.impl.IntegerPropertyDefinition;
import system.engine.world.definition.property.impl.StringPropertyDefinition;
import system.engine.world.definition.value.generator.api.ValueGeneratorFactory;
import system.engine.world.rule.enums.Type;


public class EnvironmentVariableFromXML {
    private EnvVariablesDefinitionManager envVariablesDefinitionManager=new EnvVariableDefinitionManagerImpl();

   public EnvironmentVariableFromXML(PRDEvironment prdEvironment){
       for(PRDEnvProperty prdEnvProperty:prdEvironment.getPRDEnvProperty()){
           envVariablesDefinitionManager.addEnvironmentVariable(createSingleProp(prdEnvProperty));
       }

   }

    public EnvVariablesDefinitionManager getEnvVariablesDefinitionManager() {
        return envVariablesDefinitionManager;
    }

    public PropertyDefinition createSingleProp(PRDEnvProperty prdEnvProperty){
       String propName=prdEnvProperty.getPRDName();
           Type enumValue = Type.valueOf(prdEnvProperty.getType().toUpperCase());
           PropertyDefinition propertyDefinition;
           switch (enumValue) {
               case DECIMAL:
                    //propertyDefinition=new IntegerPropertyDefinition(propName, new RandomIntegerGenerator((int) (prdEnvProperty.getPRDRange().getFrom()),(int) (prdEnvProperty.getPRDRange().getTo())));
                   propertyDefinition=new IntegerPropertyDefinition(propName, ValueGeneratorFactory.createRandomInteger((int) (prdEnvProperty.getPRDRange().getFrom()),(int) (prdEnvProperty.getPRDRange().getTo())));
                   break;
               case FLOAT:
                   propertyDefinition = new FloatPropertyDefinition(propName, ValueGeneratorFactory.createRandomFloat((float) (prdEnvProperty.getPRDRange().getFrom()),(float) (prdEnvProperty.getPRDRange().getTo())));
                   break;
               case BOOLEAN:
                    propertyDefinition=new BooleanPropertyDefinition(propName, ValueGeneratorFactory.createRandomBoolean());
                   break;
               case STRING:
                    propertyDefinition=new StringPropertyDefinition(propName, ValueGeneratorFactory.createRandomString());
                   break;
               default:
                   throw new IllegalStateException("Unexpected value: " + enumValue);
           }
           return propertyDefinition;
   }
}
