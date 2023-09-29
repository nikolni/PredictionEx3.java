package engine.per.file.jaxb2.copy;

import engine.per.file.jaxb2.generated.PRDEnvProperty;
import engine.per.file.jaxb2.generated.PRDEnvironment;
import engine.per.file.engine.world.definition.environment.variable.api.EnvVariablesDefinitionManager;
import engine.per.file.engine.world.definition.environment.variable.impl.EnvVariableDefinitionManagerImpl;
import engine.per.file.engine.world.definition.property.api.PropertyDefinition;
import engine.per.file.engine.world.definition.property.impl.BooleanPropertyDefinition;
import engine.per.file.engine.world.definition.property.impl.FloatPropertyDefinition;
import engine.per.file.engine.world.definition.property.impl.StringPropertyDefinition;
import engine.per.file.engine.world.definition.value.generator.api.ValueGeneratorFactory;
import engine.per.file.engine.world.rule.enums.Type;

public class EnvironmentVariableFromXML {
    private EnvVariablesDefinitionManager envVariablesDefinitionManager=new EnvVariableDefinitionManagerImpl();

    public EnvironmentVariableFromXML(PRDEnvironment prdEnvironment){
        for(PRDEnvProperty prdEnvProperty:prdEnvironment.getPRDEnvProperty()){
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
