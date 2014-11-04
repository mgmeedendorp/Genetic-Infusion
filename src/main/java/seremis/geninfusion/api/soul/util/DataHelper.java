package seremis.geninfusion.api.soul.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class DataHelper {

    public static Data writePrimitives(Object obj, boolean doPublic, boolean doProtected, boolean doPrivate, boolean doEmpty, boolean doFinal, boolean doBoolean, boolean doByte, boolean doShort, boolean doInt, boolean doFloat, boolean doDouble, boolean doLong, boolean doString, boolean doBooleanArr, boolean doByteArr, boolean doShortArr, boolean doIntArr, boolean doFloatArr, boolean doDoubleArr, boolean doLongArr, boolean doStringArr) {
        Data data = new Data();

        Class superClass = obj.getClass();
        while(superClass != null) {
            for(Field field : superClass.getDeclaredFields()) {
                try {
                    if(!Modifier.isStatic(field.getModifiers()) && (((Modifier.isPublic(field.getModifiers()) || !doPublic) || (Modifier.isProtected(field.getModifiers()) || !doProtected) || (Modifier.isPrivate(field.getModifiers()) || !doPrivate) || (field.getModifiers() == 0 || !doEmpty) || (Modifier.isFinal(field.getModifiers()) || !doFinal)))) {
                        field.setAccessible(true);

                        if(doBoolean && field.getType().equals(boolean.class))
                            data.setBoolean(field.getName(), field.getBoolean(obj));
                        else if(doByte && field.getType().equals(byte.class))
                            data.setByte(field.getName(), field.getByte(obj));
                        else if(doShort && field.getType().equals(short.class))
                            data.setShort(field.getName(), field.getShort(obj));
                        else if(doInt && field.getType().equals(int.class))
                            data.setInteger(field.getName(), field.getInt(obj));
                        else if(doFloat && field.getType().equals(float.class))
                            data.setFloat(field.getName(), field.getFloat(obj));
                        else if(doDouble && field.getType().equals(double.class))
                            data.setDouble(field.getName(), field.getDouble(obj));
                        else if(doLong && field.getType().equals(long.class))
                            data.setLong(field.getName(), field.getLong(obj));
                        else if(doString && field.getType().equals(String.class))
                            data.setString(field.getName(), (String) field.get(obj));

                        else if(doBooleanArr && field.getType().equals(boolean[].class))
                            data.setBooleanArray(field.getName(), (boolean[]) field.get(obj));
                        else if(doByteArr && field.getType().equals(byte[].class))
                            data.setByteArray(field.getName(), (byte[]) field.get(obj));
                        else if(doShortArr && field.getType().equals(short[].class))
                            data.setShortArray(field.getName(), (short[]) field.get(obj));
                        else if(doIntArr && field.getType().equals(int[].class))
                            data.setIntegerArray(field.getName(), (int[]) field.get(obj));
                        else if(doFloatArr && field.getType().equals(float[].class))
                            data.setFloatArray(field.getName(), (float[]) field.get(obj));
                        else if(doDoubleArr && field.getType().equals(double[].class))
                            data.setDoubleArray(field.getName(), (double[]) field.get(obj));
                        else if(doLongArr && field.getType().equals(long[].class))
                            data.setLongArray(field.getName(), (long[]) field.get(obj));
                        else if(doStringArr && field.getType().equals(String[].class))
                            data.setStringArray(field.getName(), (String[]) field.get(obj));
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            superClass = superClass.getSuperclass();
        }
        return data;
    }

    public static Data writePrimitives(Object obj) {
        return writePrimitives(obj, true, true, false, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public static Data writeAllPrimitives(Object obj) {
        return writePrimitives(obj, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public static Object applyData(Data data, Object obj, boolean doPublic, boolean doProtected, boolean doPrivate, boolean doEmpty, boolean doFinal, boolean doBoolean, boolean doByte, boolean doShort, boolean doInt, boolean doFloat, boolean doDouble, boolean doLong, boolean doString, boolean doBooleanArr, boolean doByteArr, boolean doShortArr, boolean doIntArr, boolean doFloatArr, boolean doDoubleArr, boolean doLongArr, boolean doStringArr) {
        Class superClass = obj.getClass();
        while(superClass != null) {
            for(Field field : superClass.getDeclaredFields()) {
                try {
                    if(!Modifier.isStatic(field.getModifiers()) && ((Modifier.isPublic(field.getModifiers()) || !doPublic) || (Modifier.isProtected(field.getModifiers()) || !doProtected) || (Modifier.isPrivate(field.getModifiers()) || !doPrivate) || (field.getModifiers() == 0 || !doEmpty) || (Modifier.isFinal(field.getModifiers()) || !doFinal))) {
                        field.setAccessible(true);

                        if(doBoolean && field.getType().equals(boolean.class) && data.booleanDataMap.containsKey(field.getName()))
                            field.setBoolean(obj, data.booleanDataMap.get(field.getName()));
                        else if(doByte && field.getType().equals(byte.class) && data.byteDataMap.containsKey(field.getName()))
                            field.setByte(obj, data.byteDataMap.get(field.getName()));
                        else if(doShort && field.getType().equals(short.class) && data.shortDataMap.containsKey(field.getName()))
                            field.setShort(obj, data.shortDataMap.get(field.getName()));
                        else if(doInt && field.getType().equals(int.class) && data.integerDataMap.containsKey(field.getName()))
                            field.setInt(obj, data.integerDataMap.get(field.getName()));
                        else if(doFloat && field.getType().equals(float.class) && data.floatDataMap.containsKey(field.getName()))
                            field.setFloat(obj, data.floatDataMap.get(field.getName()));
                        else if(doDouble && field.getType().equals(double.class) && data.doubleDataMap.containsKey(field.getName()))
                            field.setDouble(obj, data.doubleDataMap.get(field.getName()));
                        else if(doLong && field.getType().equals(long.class) && data.longDataMap.containsKey(field.getName()))
                            field.setLong(obj, data.longDataMap.get(field.getName()));
                        else if(doString && field.getType().equals(String.class) && data.stringDataMap.containsKey(field.getName()))
                            field.set(obj, data.stringDataMap.get(field.getName()));

                        else if(doBooleanArr && field.getType().equals(boolean[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getBooleanArray(field.getName()));
                        else if(doByteArr && field.getType().equals(byte[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getByteArray(field.getName()));
                        else if(doShortArr && field.getType().equals(short[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getShortArray(field.getName()));
                        else if(doIntArr && field.getType().equals(int[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getIntegerArray(field.getName()));
                        else if(doFloatArr && field.getType().equals(float[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getFloatArray(field.getName()));
                        else if(doDoubleArr && field.getType().equals(double[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getDoubleArray(field.getName()));
                        else if(doLongArr && field.getType().equals(long[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getLongArray(field.getName()));
                        else if(doStringArr && field.getType().equals(String[].class) && data.dataDataMap.containsKey(field.getName()))
                            field.set(obj, data.getStringArray(field.getName()));
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            superClass = superClass.getSuperclass();
        }
        return obj;
    }

    public static Object applyData(Data data, Object obj) {
        return applyData(data, obj, true, true, false, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public static Object applyAllData(Data data, Object obj) {
        return applyData(data, obj, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }
}