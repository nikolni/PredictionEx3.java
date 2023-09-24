package engine.per.file.engine.world.rule.enums;

public enum Type {
    DECIMAL {

        public Integer convert(Object value) {
            if (!(value instanceof Integer)) {
                throw new IllegalArgumentException("value " + value + " is not of a DECIMAL type (expected Integer class)");
            }
            return (Integer) value;
        }
    }, BOOLEAN {

        public Boolean convert(Object value) {
            return null;
        }
    }, FLOAT {

        public Double convert(Object value) {
            return null;
        }
    }, STRING {

        public String convert(Object value) {
            return null;
        }
    };

    public abstract <T> T convert(Object value);
}
