package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberSchemaTest {

    private static final int POSITIVE_VALUE = 5;
    private static final int ZERO_VALUE = 0;
    private static final int NEGATIVE_VALUE = -10;

    private static final int RANGE_MIN = 5;
    private static final int RANGE_MAX = 10;

    private static final int BELOW_RANGE = 4;
    private static final int ABOVE_RANGE = 11;

    @Test
    void testNumberSchemaCreation() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema).isNotNull();
    }

    @Test
    void testDefaultBehaviorWithoutRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isTrue();

        assertThat(schema.isValid(POSITIVE_VALUE)).isTrue();
        assertThat(schema.isValid(ZERO_VALUE)).isTrue();
        assertThat(schema.isValid(NEGATIVE_VALUE)).isTrue();
    }

    @Test
    void testPositiveWithoutRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number().positive();

        assertThat(schema.isValid(null)).isTrue();

        assertThat(schema.isValid(POSITIVE_VALUE)).isTrue();
        assertThat(schema.isValid(ZERO_VALUE)).isFalse();      // 0 не положительное
        assertThat(schema.isValid(NEGATIVE_VALUE)).isFalse();  // отрицательное не положительное
    }

    @Test
    void testRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(POSITIVE_VALUE)).isTrue();
        assertThat(schema.isValid(ZERO_VALUE)).isTrue();
        assertThat(schema.isValid(NEGATIVE_VALUE)).isTrue();
    }

    @Test
    void testRequiredAndPositiveTogether() {
        Validator v = new Validator();
        NumberSchema schema = v.number().positive().required();

        assertThat(schema.isValid(null)).isFalse();

        assertThat(schema.isValid(POSITIVE_VALUE)).isTrue();
        assertThat(schema.isValid(ZERO_VALUE)).isFalse();
        assertThat(schema.isValid(NEGATIVE_VALUE)).isFalse();
    }

    @Test
    void testRangeWithoutRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number().range(RANGE_MIN, RANGE_MAX);

        assertThat(schema.isValid(null)).isTrue();

        assertThat(schema.isValid(RANGE_MIN)).isTrue();   // граница включительно
        assertThat(schema.isValid(RANGE_MAX)).isTrue();   // граница включительно
        assertThat(schema.isValid(BELOW_RANGE)).isFalse();
        assertThat(schema.isValid(ABOVE_RANGE)).isFalse();
    }

    @Test
    void testRangeWithRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number().required().range(RANGE_MIN, RANGE_MAX);

        assertThat(schema.isValid(null)).isFalse();

        assertThat(schema.isValid(RANGE_MIN)).isTrue();
        assertThat(schema.isValid(RANGE_MAX)).isTrue();
        assertThat(schema.isValid(BELOW_RANGE)).isFalse();
        assertThat(schema.isValid(ABOVE_RANGE)).isFalse();
    }

    @Test
    void testCombinedRulesAccumulate() {
        Validator v = new Validator();
        NumberSchema schema = v.number()
                .required()
                .positive()
                .range(RANGE_MIN, RANGE_MAX);

        // required
        assertThat(schema.isValid(null)).isFalse();

        // positive
        assertThat(schema.isValid(NEGATIVE_VALUE)).isFalse();
        assertThat(schema.isValid(ZERO_VALUE)).isFalse();

        // range (и при этом positive тоже должен быть true)
        assertThat(schema.isValid(RANGE_MIN)).isTrue();
        assertThat(schema.isValid(RANGE_MAX)).isTrue();

        // выход за пределы
        assertThat(schema.isValid(BELOW_RANGE)).isFalse();
        assertThat(schema.isValid(ABOVE_RANGE)).isFalse();
    }
}
