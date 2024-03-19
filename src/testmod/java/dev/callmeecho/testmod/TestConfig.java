package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.config.Config;
import dev.callmeecho.cabinetapi.config.annotations.Comment;

public class TestConfig implements Config {
    @Override
    public String getName() {
        return "testconfig";
    }
    
    @Comment("This is a test string")
    public String testString = "test4";
    @Comment("This is a test int")
    public int testInt = 1;
    @Comment("This is a test boolean")
    public boolean testBool = true;
    @Comment("This is a test double")
    public double testDouble = 1.0;
    @Comment("This is a test float")
    public float testFloat = 1.0f;
    @Comment("This is a test long")
    public long testLong = 1L;
    @Comment("This is a test short")
    public short testShort = 1;
    @Comment("This is a test byte")
    public byte testByte = 1;
    @Comment("This is a test char")
    public char testChar = 'a';

    @Comment("This is a nested class")
    public Nested nested = new Nested();

    public static class Nested {
        @Comment("This is a nested string\n" +
                "With a new line")
        public String nestedString = "test";
    }
}
