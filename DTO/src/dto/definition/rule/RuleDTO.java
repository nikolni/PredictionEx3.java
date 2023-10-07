package dto.definition.rule;

import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.activation.ActivationDTO;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.List;

public class RuleDTO{
    private final String name;
    private final List<AbstractActionDTO> actionDTOS;
    private ActivationDTO activation;
    private final List<String> actionNames;

    public RuleDTO(String name, List<String> actionNames, List<AbstractActionDTO> actionDTOS, ActivationDTO activation) {
        this.name = name;
        this.actionDTOS = actionDTOS;
        this.actionNames =actionNames;
        this.activation = activation;
    }


    public String getName() {
        return name;
    }

    public ActivationDTO getActivation() {
        return activation;
    }

    public int getNumOfActions() {
        return actionDTOS.size();
    }

    public List<AbstractActionDTO> getActions() {
        return actionDTOS;
    }

    public List<String> getActionsNames() {
        return actionNames;
    }

    private class RuleDeserializer implements JsonDeserializer<RuleDTO> {

        @Override
        public RuleDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            // extract raw data
            String ruleName = json.getAsJsonObject().get("name").getAsString();
            ActivationDTO active = json.getAsJsonObject().get("activation").getAsString();
            List<String> actionsNames = json.getAsJsonObject().get("actionNames").getAsString();

            List<AbstractActionDTO> actions = json.getAsJsonObject().get("actionDTOS").getAsString();

            // build object manually
            Person result = new Person();
            result.setName(personName);
            result.setAge(personAge);

            // determine manually the interface concrete class
            WindBlower windBlower = new WindBlower();
            windBlower.setWindPower(windPower);

            // or tell GSON exactly which object you would like to construct a specific object from the (sub) object
            // that was originally declared through an interface reference
            //WindBlower windBlower = context.deserialize(json.getAsJsonObject().get("windBlower"), WindBlower.class);

            result.setWindBlower(windBlower);

            return result;
        }
    }
}
