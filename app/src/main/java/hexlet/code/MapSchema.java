package hexlet.code;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    private static final String CHECK_REQUIRED = "required";
    private static final String CHECK_SIZEOF = "sizeof";

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
}
