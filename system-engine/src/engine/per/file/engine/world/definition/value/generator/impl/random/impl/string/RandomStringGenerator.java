package engine.per.file.engine.world.definition.value.generator.impl.random.impl.string;


import engine.per.file.engine.world.definition.value.generator.impl.random.api.AbstractRandomValueGenerator;

public class RandomStringGenerator extends AbstractRandomValueGenerator<String> {
    private final static int maxRange  = 50;
    @Override
    public String generateValue() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !?,_-().";
        StringBuilder sb = new StringBuilder(maxRange);

        for (int i = 0; i < maxRange; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
