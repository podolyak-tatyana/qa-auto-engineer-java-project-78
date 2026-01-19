package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapSchemaTest {

    private static final int SIZE_ZERO = 0;
    private static final int SIZE_ONE = 1;
    private static final int SIZE_TWO = 2;
    private static final int MIN_LASTNAME_LENGTH = 2;


    @Test
    void testMapSchemaCreation() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema).isNotNull();
    }

    @Test
    void testDefaultBehaviorWithoutRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue();

        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key", "value");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testSizeofWithoutRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map().sizeof(SIZE_ONE);


        assertThat(schema.isValid(null)).isTrue();

        assertThat(schema.isValid(new HashMap<>())).isFalse();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();

        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isFalse();
    }

    @Test
    void testSizeofWithRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map().required().sizeof(SIZE_ZERO);

        assertThat(schema.isValid(null)).isFalse();

        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isFalse();
    }

    @Test
    void testCombinedRulesAccumulate() {
        Validator v = new Validator();
        MapSchema schema = v.map().required().sizeof(SIZE_TWO);

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");

        assertThat(schema.isValid(data)).isFalse();

        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testSizeofLastCallOverridesPrevious() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");

        schema.sizeof(SIZE_TWO).sizeof(SIZE_ONE);

        assertThat(schema.isValid(data)).isTrue();

        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isFalse();
    }

    @Test
    void testShapeValidationExample() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(MIN_LASTNAME_LENGTH));

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertThat(schema.isValid(human1)).isTrue();

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertThat(schema.isValid(human2)).isFalse();

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertThat(schema.isValid(human3)).isFalse();
    }

    @Test
    void testShapeDoesNotBreakNullWithoutRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());

        schema.shape(schemas);

        assertThat(schema.isValid(null)).isTrue();
    }

    @Test
    void testShapeWithRequiredMakesNullInvalid() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());

        schema.shape(schemas).required();

        // required() запрещает null
        assertThat(schema.isValid(null)).isFalse();
    }

    @Test
    void testShapeLastCallOverridesPrevious() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas1 = new HashMap<>();
        schemas1.put("firstName", v.string().required());

        Map<String, BaseSchema<?>> schemas2 = new HashMap<>();
        schemas2.put("lastName", v.string().required().minLength(MIN_LASTNAME_LENGTH));

        schema.shape(schemas1).shape(schemas2);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", null);
        human.put("lastName", "Li");

        assertThat(schema.isValid(human)).isTrue();

        human.put("lastName", "B");
        assertThat(schema.isValid(human)).isFalse();
    }
}
