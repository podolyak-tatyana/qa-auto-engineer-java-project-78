package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapSchemaTest {

    private static final int SIZE_ZERO = 0;
    private static final int SIZE_ONE = 1;
    private static final int SIZE_TWO = 2;

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
}
