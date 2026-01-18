package hexlet.code;

public class StringSchema extends BaseSchema<String> {
    private static final String CHECK_REQUIRED = "required";
    private static final String CHECK_MIN_LENGTH = "minLength";
    private static final String CHECK_CONTAINS = "contains";

    public StringSchema required() {
        addCheck(CHECK_REQUIRED, value -> value != null && !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int min) {
        addCheck(CHECK_MIN_LENGTH, value -> {
            if (!hasCheck(CHECK_REQUIRED) && (value == null || value.isEmpty())) {
                return true;
            }
            if (value == null) {
                return false;
            }
            return value.length() >= min;
        });
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck(CHECK_CONTAINS, value -> {
            if (!hasCheck(CHECK_REQUIRED) && (value == null || value.isEmpty())) {
                return true;
            }
            if (value == null) {
                return false;
            }
            return value.contains(substring);
        });
        return this;
    }
}
