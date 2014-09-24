package seremis.geninfusion.misc;

import java.util.HashMap;
import java.util.Map;

public class Data {

    public Map<String, Integer> integerDataMap = new HashMap<String, Integer>();
    public HashMap<String, Float> floatDataMap = new HashMap<String, Float>();
    public HashMap<String, Double> doubleDataMap = new HashMap<String, Double>();
    public HashMap<String, Long> longDataMap = new HashMap<String, Long>();
    public Map<String, Boolean> booleanDataMap = new HashMap<String, Boolean>();
    public HashMap<String, String> stringDataMap = new HashMap<String, String>();
    public HashMap<String, Byte> byteDataMap = new HashMap<String, Byte>();
    public HashMap<String, Short> shortDataMap = new HashMap<String, Short>();
    public HashMap<String, Character> charDataMap = new HashMap<String, Character>();
    public HashMap<String, Data> dataDataMap = new HashMap<String, Data>();

    public boolean getBoolean(String key) {
        return booleanDataMap.get(key);
    }

    public double getDouble(String key) {
        return doubleDataMap.get(key) == null ? -1 : doubleDataMap.get(key);
    }

    public float getFloat(String key) {
        return floatDataMap.get(key) == null ? -1 : floatDataMap.get(key);
    }

    public int getInteger(String key) {
        return integerDataMap.get(key) == null ? -1 : integerDataMap.get(key);
    }

    public byte getByte(String key) {
        return byteDataMap.get(key) == null ? -1 : byteDataMap.get(key);
    }

    public short getShort(String key) {
        return shortDataMap.get(key) == null ? -1 : shortDataMap.get(key);
    }

    public long getLong(String key) { return longDataMap.get(key) == null ? -1 : longDataMap.get(key); }

    public String getString(String key) {
        return stringDataMap.get(key);
    }

    public char getChar(String key) { return charDataMap.get(key); }

    public Data getData(String key) {
        return dataDataMap.get(key);
    }

    public void setBoolean(String key, boolean value) {
        booleanDataMap.put(key, value);
    }

    public void setDouble(String key, double value) {
        doubleDataMap.put(key, value);
    }

    public void setFloat(String key, float value) {
        floatDataMap.put(key, value);
    }

    public void setInteger(String key, int value) {
        integerDataMap.put(key, value);
    }

    public void setByte(String key, byte value) {
        byteDataMap.put(key, value);
    }

    public void setShort(String key, short value) {
        shortDataMap.put(key, value);
    }

    public void setString(String key, String value) {
        stringDataMap.put(key, value);
    }

    public void setLong(String key, long value) {
        longDataMap.put(key, value);
    }

    public void setChar(String key, char value) {
        charDataMap.put(key, value);
    }

    public void setData(String key, Data value) {
        dataDataMap.put(key, value);
    }

    public String toString() {
        return "Data[booleans: " + booleanDataMap + ", bytes: " + byteDataMap + ", shorts: " + shortDataMap + ", integers: " + integerDataMap + ", floats: " + floatDataMap + ", doubles: " + doubleDataMap + ", longs: " + longDataMap + ", chars: " + charDataMap + ", strings: " + stringDataMap + ", data: " + dataDataMap + "]";
    }
}
