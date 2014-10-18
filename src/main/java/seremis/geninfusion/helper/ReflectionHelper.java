package seremis.geninfusion.helper;

import java.lang.reflect.Field;

public class ReflectionHelper {

    public static boolean getBooleanField(Object obj, String name) {
        boolean value = false;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.getBoolean(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setBooleanField(Object obj, String name, boolean value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.setBoolean(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte getByteField(Object obj, String name) {
        byte value = 0;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.getByte(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setByteField(Object obj, String name, byte value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.setByte(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static short getShortField(Object obj, String name) {
        short value = 0;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.getShort(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setShortField(Object obj, String name, short value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.setShort(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getIntegerField(Object obj, String name) {
        int value = 0;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.getInt(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void getIntegerField(Object obj, String name, int value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.setInt(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static float getFloatField(Object obj, String name) {
        float value = 0.0F;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.getFloat(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setFloatField(Object obj, String name, float value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.setFloat(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static double getDoubleField(Object obj, String name) {
        double value = 0.0D;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.getDouble(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setDoubleField(Object obj, String name, double value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.setDouble(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static long getLongField(Object obj, String name) {
        long value = 0;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.getLong(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setLongField(Object obj, String name, long value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.setLong(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String setStringField(Object obj, String name) {
        String value = null;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = (String) field.get(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setStringField(Object obj, String name, String value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.set(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object getObjectField(Object obj, String name) {
        Object value = null;

        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        value = field.get(obj);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void setObjectField(Object obj, String name, Object value) {
        Class superClass = obj.getClass();
        loop: while(superClass != null) {
            for (Field field : superClass.getDeclaredFields()) {
                try {
                    if(field.getName().equals(name)) {
                        field.set(obj, value);
                        break loop;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
