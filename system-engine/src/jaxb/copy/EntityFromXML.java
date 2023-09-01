package jaxb.copy;

import jaxb.generated.PRDEntities;
import jaxb.generated.PRDEntity;
import jaxb.generated.PRDProperty;
import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.definition.entity.impl.EntityDefinitionImpl;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.definition.entity.manager.impl.EntityDefinitionManagerImpl;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.definition.property.impl.BooleanPropertyDefinition;
import system.engine.world.definition.property.impl.FloatPropertyDefinition;
import system.engine.world.definition.property.impl.IntegerPropertyDefinition;
import system.engine.world.definition.property.impl.StringPropertyDefinition;
import system.engine.world.definition.value.generator.api.ValueGeneratorFactory;
import system.engine.world.rule.enums.Type;

public class EntityFromXML {
    private EntityDefinitionManager entityDefinitionManager=new EntityDefinitionManagerImpl();



    public EntityFromXML(PRDEntities prdEntities){
        for(PRDEntity prdEntity:prdEntities.getPRDEntity()) {
            entityDefinitionManager.addEntityDefinition(createEntityDef(prdEntity));
        }
    }

    public EntityDefinitionManager getEntityDefinitionManager() {
        return entityDefinitionManager;
    }

    public EntityDefinition createEntityDef(PRDEntity prdEntity){
        EntityDefinition entityDefinition=new EntityDefinitionImpl(prdEntity.getName(),prdEntity.getPRDPopulation());
        for(PRDProperty prdProperty:prdEntity.getPRDProperties().getPRDProperty()){
            entityDefinition.addPropertyDefinition(createPropertyDef(prdProperty));
        }
        return entityDefinition;
    }

    public PropertyDefinition createPropertyDef(PRDProperty prdProperty){
        String propName=prdProperty.getPRDName();

            Type enumValue = Type.valueOf(prdProperty.getType().toUpperCase());
            PropertyDefinition propertyDefinition;
            if(prdProperty.getPRDValue().isRandomInitialize()){
                switch (enumValue) {

                    case DECIMAL:
                        //propertyDefinition=new IntegerPropertyDefinition(propName, new RandomIntegerGenerator((int) (prdEnvProperty.getPRDRange().getFrom()),(int) (prdEnvProperty.getPRDRange().getTo())));
                        propertyDefinition=new IntegerPropertyDefinition(propName, ValueGeneratorFactory.createRandomInteger((int) (prdProperty.getPRDRange().getFrom()),(int) (prdProperty.getPRDRange().getTo())));
                        break;
                    case FLOAT:
                        propertyDefinition = new FloatPropertyDefinition(propName, ValueGeneratorFactory.createRandomFloat((float) (prdProperty.getPRDRange().getFrom()),(float) (prdProperty.getPRDRange().getTo())));
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
        }
        else { //init
                switch (enumValue) {
                    case DECIMAL:
                        //propertyDefinition=new IntegerPropertyDefinition(propName, new RandomIntegerGenerator((int) (prdEnvProperty.getPRDRange().getFrom()),(int) (prdEnvProperty.getPRDRange().getTo())));
                        propertyDefinition=new IntegerPropertyDefinition(propName, ValueGeneratorFactory.createFixedInteger((int) (prdProperty.getPRDRange().getFrom()),
                                (int) (prdProperty.getPRDRange().getTo()), Integer.parseInt(prdProperty.getPRDValue().getInit())));
                        break;
                    case FLOAT:
                        propertyDefinition = new FloatPropertyDefinition(propName, ValueGeneratorFactory.createFixedFloat((float) (prdProperty.getPRDRange().getFrom()),
                                (float) (prdProperty.getPRDRange().getTo()), Float.parseFloat(prdProperty.getPRDValue().getInit())));
                        break;
                    case BOOLEAN:
                        propertyDefinition=new BooleanPropertyDefinition(propName, ValueGeneratorFactory.createFixedBoolean(Boolean.parseBoolean(prdProperty.getPRDValue().getInit())));
                        break;
                    case STRING:
                        propertyDefinition=new StringPropertyDefinition(propName, ValueGeneratorFactory.createFixedString(prdProperty.getPRDValue().getInit()));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + enumValue);
                }
        }
        return propertyDefinition;
    }


}
