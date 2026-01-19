package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {
    private static final String CHECK_REQUIRED = "required";
    private static final String CHECK_POSITIVE = "positive";
    private static final String CHECK_RANGE = "range";

    public NumberSchema required() {
        addCheck(CHECK_REQUIRED, Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addCheck(CHECK_POSITIVE, value -> {
            if (!hasCheck(CHECK_REQUIRED) && value == null) {
                return true;
            }
            if (value == null) {
                return false;
            }
            return value > 0;
        });
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck(CHECK_RANGE, value -> {
            if (!hasCheck(CHECK_REQUIRED) && value == null) {
                return true;
            }
            if (value == null) {
                return false;
            }
            return value >= min && value <= max;
        });
        return this;
    }
}
