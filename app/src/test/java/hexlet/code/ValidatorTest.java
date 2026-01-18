package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    @Test
    void testStringSchemaCreation() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema).isNotNull();
    }

    @Test
    void testStringSchemaDefaultBehavior() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();

        assertThat(schema.isValid("hello")).isTrue();
    }

    @Test
    void testRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("what does the fox say")).isTrue();
    }

    @Test
    void testMinLengthWithoutRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().minLength(5);

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();

        assertThat(schema.isValid("abcd")).isFalse();
        assertThat(schema.isValid("abcde")).isTrue();
        assertThat(schema.isValid("abcdef")).isTrue();
    }

    @Test
    void testMinLengthWithRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(3);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();

        assertThat(schema.isValid("ab")).isFalse();
        assertThat(schema.isValid("abc")).isTrue();
    }

    @Test
    void testContainsWithoutRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().contains("hex");

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("what does the fox say")).isFalse();
    }

    @Test
    void testContainsWithRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().contains("wh");

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();

        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hello")).isFalse();
    }

    @Test
    void testCombinedRulesAccumulate() {
        Validator v = new Validator();
        StringSchema schema = v.string()
                .required()
                .minLength(5)
                .contains("hex");

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();

        assertThat(schema.isValid("he")).isFalse();

        assertThat(schema.isValid("hello")).isFalse();

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("some hex value")).isTrue();
    }

}
