package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.config.Config;
import dev.callmeecho.cabinetapi.config.NestedConfig;
import dev.callmeecho.cabinetapi.config.annotations.Comment;
import dev.callmeecho.cabinetapi.config.annotations.Range;
import dev.callmeecho.cabinetapi.config.annotations.Sync;
import net.minecraft.util.Identifier;

public class TestConfig implements Config {
    @Override
    public Identifier getName() {
        return Identifier.of("testmod", "testconfig");
    }
    
    @Comment("This is a test string")
    @Sync
    public String testString = "test4";
    @Comment("This is a test int")
    @Range(min = 2, max = 10)
    public int testInt = 1;
    public boolean testBool = true;
    @Comment("This is a test double")
    public double testDouble = 1.0;
    @Comment("This is a test float")
    public float testFloat = 1.0f;

    @Comment("This is a nested class")
    public Nested nested = new Nested();

    @Comment("This is a test enum")
    public TestEnum testEnum = TestEnum.TEST_1;

    public static class Nested implements NestedConfig {
        @Comment("This is a nested string\n" +
                "With a new line")
        public String nestedString = "test";

        @Override
        public Identifier getName() {
            return Identifier.of("testmod", "nested");
        }

        @Comment("This is a nested nested class")
        public NestedNested nestedNested = new NestedNested();

        public static class NestedNested implements NestedConfig {
            @Comment("This is a nested nested string")
            public String nestedNestedString = "test";

            @Override
            public Identifier getName() {
                return Identifier.of("testmod", "nestednested");
            }
        }
    }

    public enum TestEnum {
        TEST_1,
        TEST_2,
        TEST_3
    }
}
