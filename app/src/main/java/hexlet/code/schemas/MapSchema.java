package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    private static final String CHECK_REQUIRED = "required";
    private static final String CHECK_SIZEOF = "sizeof";
    private static final String CHECK_SHAPE = "shape";

    public MapSchema required() {
        addCheck(CHECK_REQUIRED, value -> value != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck(CHECK_SIZEOF, value -> {
            if (!hasCheck(CHECK_REQUIRED) && value == null) {
                return true;
            }
            if (value == null) {
                return false;
            }
            return value.size() == size;
        });
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        addCheck(CHECK_SHAPE, value -> {
            // без required() null валиден
            if (!hasCheck(CHECK_REQUIRED) && value == null) {
                return true;
            }
            if (value == null) {
                return false;
            }

            for (var entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema schema = entry.getValue();

                Object fieldValue = value.get(key);
                if (!schema.isValid(fieldValue)) {
                    return false;
                }
            }

            return true;
        });

        return this;
    }
}
