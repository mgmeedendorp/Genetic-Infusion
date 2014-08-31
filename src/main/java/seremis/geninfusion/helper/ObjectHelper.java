package seremis.geninfusion.helper;

import seremis.geninfusion.misc.Data;

import java.lang.reflect.Field;

public class ObjectHelper {

    public static Data saveObject(Object obj) {
        Data data = new Data();

        for(Field field : obj.getClass().getFields()) {
            try {
                if (field.getType().equals(byte.class)) data.setByte(field.getName(), field.getByte(obj));
                if (field.getType().equals(short.class)) data.setShort(field.getName(), field.getShort(obj));
                if (field.getType().equals(int.class)) data.setInteger(field.getName(), field.getInt(obj));
                if (field.getType().equals(float.class)) data.setFloat(field.getName(), field.getFloat(obj));
                if (field.getType().equals(double.class)) data.setDouble(field.getName(), field.getDouble(obj));
                if (field.getType().equals(long.class)) data.setLong(field.getName(), field.getLong(obj));
                if (field.getType().equals(char.class)) data.setChar(field.getName(), field.getChar(obj));
                if (field.getType().equals(String.class)) data.setString(field.getName(), (String) field.get(obj));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    public static void populateObject(Object obj, Data data) {
        for(int i = 0; i < obj.getClass().getFields().length; i++) {
            Field field = obj.getClass().getFields()[i];
            try {
                if(field.getType().equals(byte.class))field.setByte(obj, data.getByte(field.getName()));
                if(field.getType().equals(short.class))field.setShort(obj, data.getShort(field.getName()));
                if(field.getType().equals(int.class))field.setInt(obj, data.getInteger(field.getName()));
                if(field.getType().equals(long.class))field.setLong(obj, data.getLong(field.getName()));
                if(field.getType().equals(float.class))field.setFloat(obj, data.getFloat(field.getName()));
                if(field.getType().equals(double.class))field.setDouble(obj, data.getDouble(field.getName()));
                if(field.getType().equals(boolean.class))field.setBoolean(obj, data.getBoolean(field.getName()));
                if(field.getType().equals(char.class))field.setChar(obj, data.getChar(field.getName()));
                if(field.getType().equals(String.class))field.set(obj, data.getString(field.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
