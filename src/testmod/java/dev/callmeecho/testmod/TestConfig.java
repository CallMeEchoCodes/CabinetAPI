package dev.callmeecho.testmod;

import dev.callmeecho.cabinetapi.config.Config;
import dev.callmeecho.cabinetapi.config.annotations.Comment;

public class TestConfig implements Config {
    @Override
    public String getName() {
        return "testconfig";
    }
    
    @Comment("This is a test string")
    public final String testString = "test";
    public int testInt = 1;
    public boolean testBool = true;
    public double testDouble = 1.0;
    public float testFloat = 1.0f;
    public long testLong = 1L;
    public short testShort = 1;
    public byte testByte = 1;
    public char testChar = 'a';
}
