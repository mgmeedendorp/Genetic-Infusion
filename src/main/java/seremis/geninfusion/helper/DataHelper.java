package seremis.geninfusion.helper;

import seremis.geninfusion.api.soul.util.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class DataHelper {

    public static Data writePrimitives(Object obj, boolean doBoolean, boolean doByte, boolean doShort, boolean doInt, boolean doFloat, boolean doDouble, boolean doLong, boolean doString, boolean doBooleanArr, boolean doByteArr, boolean doShortArr, boolean doIntArr, boolean doFloatArr, boolean doDoubleArr, boolean doLongArr, boolean doStringArr) {
        Data data = new Data();

        Class superClass = obj.getClass();
        while(superClass != null) {
            for(Field field : superClass.getDeclaredFields()) {
                try {
                    if ((Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers()) || field.getModifiers() == 0) && !Modifier.isFinal(field.getModifiers())) {
                        field.setAccessible(true);

                        if(doBoolean && field.getType().equals(boolean.class)) data.setBoolean(field.getName(), field.getBoolean(obj));
                        else if (doByte && field.getType().equals(byte.class)) data.setByte(field.getName(), field.getByte(obj));
                        else if (doShort && field.getType().equals(short.class)) data.setShort(field.getName(), field.getShort(obj));
                        else if (doInt && field.getType().equals(int.class)) data.setInteger(field.getName(), field.getInt(obj));
                        else if (doFloat && field.getType().equals(float.class)) data.setFloat(field.getName(), field.getFloat(obj));
                        else if (doDouble && field.getType().equals(double.class)) data.setDouble(field.getName(), field.getDouble(obj));
                        else if (doLong && field.getType().equals(long.class)) data.setLong(field.getName(), field.getLong(obj));
                        else if (doString && field.getType().equals(String.class)) data.setString(field.getName(), (String) field.get(obj));

                        else if (doBooleanArr && field.getType().equals(boolean[].class)) data.setBooleanArray(field.getName(), (boolean[]) field.get(obj));
                        else if (doByteArr && field.getType().equals(byte[].class)) data.setByteArray(field.getName(), (byte[]) field.get(obj));
                        else if (doShortArr && field.getType().equals(short[].class)) data.setShortArray(field.getName(), (short[]) field.get(obj));
                        else if (doIntArr && field.getType().equals(int[].class)) data.setIntegerArray(field.getName(), (int[]) field.get(obj));
                        else if (doFloatArr && field.getType().equals(float[].class)) data.setFloatArray(field.getName(), (float[]) field.get(obj));
                        else if (doDoubleArr && field.getType().equals(double[].class)) data.setDoubleArray(field.getName(), (double[]) field.get(obj));
                        else if (doLongArr && field.getType().equals(long[].class)) data.setLongArray(field.getName(), (long[]) field.get(obj));
                        else if (doStringArr && field.getType().equals(String[].class)) data.setStringArray(field.getName(), (String[]) field.get(obj));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            superClass = superClass.getSuperclass();
        }
        return data;
    }

    public static Data writePrimitives(Object obj) {
        return writePrimitives(obj, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }
}