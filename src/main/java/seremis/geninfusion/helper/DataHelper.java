package seremis.geninfusion.helper;

import seremis.geninfusion.misc.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class DataHelper {

    public static Data writePrimitives(Object obj) {
        Data data = new Data();

        Class superClass = obj.getClass();
        while(superClass != null) {
            for(Field field : superClass.getDeclaredFields()) {
                try {
                    if ((Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers()) || field.getModifiers() == 0) && !Modifier.isFinal(field.getModifiers())) {

                        field.setAccessible(true);

                        if (field.getType().equals(byte.class)) data.setByte(field.getName(), field.getByte(obj));
                        else if (field.getType().equals(short.class)) data.setShort(field.getName(), field.getShort(obj));
                        else if (field.getType().equals(int.class)) data.setInteger(field.getName(), field.getInt(obj));
                        else if (field.getType().equals(float.class)) data.setFloat(field.getName(), field.getFloat(obj));
                        else if (field.getType().equals(double.class)) data.setDouble(field.getName(), field.getDouble(obj));
                        else if (field.getType().equals(long.class)) data.setLong(field.getName(), field.getLong(obj));
                        else if (field.getType().equals(char.class)) data.setChar(field.getName(), field.getChar(obj));
                        else if (field.getType().equals(String.class)) data.setString(field.getName(), (String) field.get(obj));

                        else if (field.getType().equals(byte[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((byte[]) field.get(obj)).length; i++) {
                                byte d = ((byte[]) field.get(obj))[i];
                                data2.setByte(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);
                        } else if (field.getType().equals(short[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((short[]) field.get(obj)).length; i++) {
                                short d = ((short[]) field.get(obj))[i];
                                data2.setShort(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);
                        } else if (field.getType().equals(int[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((int[]) field.get(obj)).length; i++) {
                                int d = ((int[]) field.get(obj))[i];
                                data2.setInteger(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);
                        } else if (field.getType().equals(float[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((float[]) field.get(obj)).length; i++) {
                                float d = ((float[]) field.get(obj))[i];
                                data2.setFloat(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);
                        } else if (field.getType().equals(double[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((double[]) field.get(obj)).length; i++) {
                                double d = ((double[]) field.get(obj))[i];
                                data2.setDouble(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);
                        } else if (field.getType().equals(long[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((long[]) field.get(obj)).length; i++) {
                                long d = ((long[]) field.get(obj))[i];
                                data2.setLong(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);
                        } else if (field.getType().equals(char[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((char[]) field.get(obj)).length; i++) {
                                char d = ((char[]) field.get(obj))[i];
                                data2.setChar(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);
                        } else if (field.getType().equals(String[].class)) {
                            Data data2 = new Data();
                            for (int i = 0; i < ((String[]) field.get(obj)).length; i++) {
                                String d = ((String[]) field.get(obj))[i];
                                data2.setString(field.getName() + "." + i, d);
                            }
                            data.setData(field.getName(), data2);

                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
            superClass = superClass.getSuperclass();
        }
        return data;
    }
}