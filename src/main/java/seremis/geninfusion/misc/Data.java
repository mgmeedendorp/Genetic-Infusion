package seremis.geninfusion.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import seremis.geninfusion.util.INBTTagable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data implements INBTTagable {

    public HashMap<String, Integer> integerDataMap = new HashMap<String, Integer>();
    public HashMap<String, Float> floatDataMap = new HashMap<String, Float>();
    public HashMap<String, Double> doubleDataMap = new HashMap<String, Double>();
    public HashMap<String, Long> longDataMap = new HashMap<String, Long>();
    public HashMap<String, Boolean> booleanDataMap = new HashMap<String, Boolean>();
    public HashMap<String, String> stringDataMap = new HashMap<String, String>();
    public HashMap<String, Byte> byteDataMap = new HashMap<String, Byte>();
    public HashMap<String, Short> shortDataMap = new HashMap<String, Short>();
    public HashMap<String, Data> dataDataMap = new HashMap<String, Data>();
    public HashMap<String, NBTTagCompound> nbtDataMap = new HashMap<String, NBTTagCompound>();

    public Data() {}

    public Data(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    public boolean getBoolean(String key) {
        return booleanDataMap.get(key) == null ? false : booleanDataMap.get(key);
    }

    public double getDouble(String key) {
        return doubleDataMap.get(key) == null ? 0 : doubleDataMap.get(key);
    }

    public float getFloat(String key) {
        return floatDataMap.get(key) == null ? 0 : floatDataMap.get(key);
    }

    public int getInteger(String key) {
        return integerDataMap.get(key) == null ? 0 : integerDataMap.get(key);
    }

    public byte getByte(String key) {
        return byteDataMap.get(key) == null ? 0 : byteDataMap.get(key);
    }

    public short getShort(String key) {
        return shortDataMap.get(key) == null ? 0 : shortDataMap.get(key);
    }

    public long getLong(String key) {
        return longDataMap.get(key) == null ? 0 : longDataMap.get(key);
    }

    public String getString(String key) {
        return stringDataMap.get(key);
    }

    public Data getData(String key) {
        return dataDataMap.get(key);
    }

    public NBTTagCompound getNBT(String key) {
        return nbtDataMap.get(key);
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

    public void setData(String key, Data value) {
        dataDataMap.put(key, value);
    }

    public void setNBT(String key, NBTTagCompound value) {
        nbtDataMap.put(key, value);
    }

    public String toString() {
        return "Data[booleans: " + booleanDataMap + ", bytes: " + byteDataMap + ", shorts: " + shortDataMap + ", integers: " + integerDataMap + ", floats: " + floatDataMap + ", doubles: " + doubleDataMap + ", longs: " + longDataMap + ", strings: " + stringDataMap + ", data: " + dataDataMap + ", nbt: " + nbtDataMap + "]";
    }

    public boolean isEmpty() {
        return booleanDataMap.isEmpty() && byteDataMap.isEmpty() && shortDataMap.isEmpty() && integerDataMap.isEmpty() && floatDataMap.isEmpty() && doubleDataMap.isEmpty() && longDataMap.isEmpty() && stringDataMap.isEmpty() && nbtDataMap.isEmpty() && dataDataMap.isEmpty();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        ArrayList<String> stringList = new ArrayList<String>();
        if (!booleanDataMap.isEmpty()) {
            stringList.addAll(booleanDataMap.keySet());
            NBTTagCompound booleanCompound = new NBTTagCompound();
            for (int i = 0; i < booleanDataMap.size(); i++) {
                booleanCompound.setString("boolean" + i + "Name", stringList.get(i));
                booleanCompound.setBoolean("boolean" + i + "Value", booleanDataMap.get(stringList.get(i)));
            }
            booleanCompound.setString("type", "boolean");
            booleanCompound.setInteger("size", booleanDataMap.size());
            tagList.appendTag(booleanCompound);
            stringList.clear();
        }
        if (!byteDataMap.isEmpty()) {
            stringList.addAll(byteDataMap.keySet());
            NBTTagCompound byteCompound = new NBTTagCompound();
            for (int i = 0; i < byteDataMap.size(); i++) {
                byteCompound.setString("byte" + i + "Name", stringList.get(i));
                byteCompound.setByte("byte" + i + "Value", byteDataMap.get(stringList.get(i)));
            }
            byteCompound.setString("type", "byte");
            byteCompound.setInteger("size", byteDataMap.size());
            tagList.appendTag(byteCompound);
            stringList.clear();
        }
        if (!shortDataMap.isEmpty()) {
            stringList.addAll(shortDataMap.keySet());
            NBTTagCompound shortCompound = new NBTTagCompound();
            for (int i = 0; i < shortDataMap.size(); i++) {
                shortCompound.setString("short" + i + "Name", stringList.get(i));
                shortCompound.setShort("short" + i + "Value", shortDataMap.get(stringList.get(i)));
            }
            shortCompound.setString("type", "short");
            shortCompound.setInteger("size", shortDataMap.size());
            tagList.appendTag(shortCompound);
            stringList.clear();
        }
        if (!integerDataMap.isEmpty()) {
            stringList.addAll(integerDataMap.keySet());
            NBTTagCompound integerCompound = new NBTTagCompound();
            for (int i = 0; i < integerDataMap.size(); i++) {
                integerCompound.setString("integer" + i + "Name", stringList.get(i));
                integerCompound.setInteger("integer" + i + "Value", integerDataMap.get(stringList.get(i)));
            }
            integerCompound.setString("type", "integer");
            integerCompound.setInteger("size", integerDataMap.size());
            tagList.appendTag(integerCompound);
            stringList.clear();
        }
        if (!floatDataMap.isEmpty()) {
            stringList.addAll(floatDataMap.keySet());
            NBTTagCompound floatCompound = new NBTTagCompound();
            for (int i = 0; i < floatDataMap.size(); i++) {
                floatCompound.setString("float" + i + "Name", stringList.get(i));
                floatCompound.setFloat("float" + i + "Value", floatDataMap.get(stringList.get(i)));
            }
            floatCompound.setString("type", "float");
            floatCompound.setInteger("size", floatDataMap.size());
            stringList.clear();
        }
        if (!doubleDataMap.isEmpty()) {
            stringList.addAll(doubleDataMap.keySet());
            NBTTagCompound doubleCompound = new NBTTagCompound();
            for (int i = 0; i < doubleDataMap.size(); i++) {
                doubleCompound.setString("double" + i + "Name", stringList.get(i));
                doubleCompound.setDouble("double" + i + "Value", doubleDataMap.get(stringList.get(i)));
            }
            doubleCompound.setString("type", "double");
            doubleCompound.setInteger("size", doubleDataMap.size());
            stringList.clear();
        }
        if (!longDataMap.isEmpty()) {
            stringList.addAll(longDataMap.keySet());
            NBTTagCompound longCompound = new NBTTagCompound();
            for (int i = 0; i < longDataMap.size(); i++) {
                longCompound.setString("long" + i + "Name", stringList.get(i));
                longCompound.setLong("long" + i + "Value", longDataMap.get(stringList.get(i)));
            }
            longCompound.setString("type", "long");
            longCompound.setInteger("size", longDataMap.size());
            stringList.clear();
        }
        if (!stringDataMap.isEmpty()) {
            stringList.addAll(stringDataMap.keySet());
            NBTTagCompound stringCompound = new NBTTagCompound();
            for (int i = 0; i < stringDataMap.size(); i++) {
                stringCompound.setString("string" + i + "Name", stringList.get(i));
                stringCompound.setString("string" + i + "Value", stringDataMap.get(stringList.get(i)));
            }
            stringCompound.setString("type", "string");
            stringCompound.setInteger("size", stringDataMap.size());
            stringList.clear();
        }
        if (!nbtDataMap.isEmpty()) {
            stringList.addAll(nbtDataMap.keySet());
            NBTTagCompound nbtCompound = new NBTTagCompound();
            for (int i = 0; i < nbtDataMap.size(); i++) {
                nbtCompound.setString("nbt" + i + "Name", stringList.get(i));
                nbtCompound.setTag("nbt" + i + "Value", nbtDataMap.get(stringList.get(i)));
            }
            nbtCompound.setString("type", "nbt");
            nbtCompound.setInteger("size", nbtDataMap.size());
            stringList.clear();
        }
        if (!dataDataMap.isEmpty()) {
            stringList.addAll(dataDataMap.keySet());
            NBTTagCompound dataCompound = new NBTTagCompound();
            for (int i = 0; i < dataDataMap.size(); i++) {
                dataCompound.setString("data" + i + "Name", stringList.get(i));
                NBTTagCompound cmp = new NBTTagCompound();
                dataDataMap.get(stringList.get(i)).writeToNBT(cmp);

                dataCompound.setTag("data" + i + "Value", cmp);
            }
            dataCompound.setString("type", "data");
            dataCompound.setInteger("size", dataDataMap.size());
            stringList.clear();
        }
        compound.setTag("data", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("data")) {
            NBTTagList tagList = compound.getTagList("data", Constants.NBT.TAG_COMPOUND);
            NBTTagCompound compoundBoolean = null;
            NBTTagCompound compoundByte = null;
            NBTTagCompound compoundShort = null;
            NBTTagCompound compoundInteger = null;
            NBTTagCompound compoundFloat = null;
            NBTTagCompound compoundDouble = null;
            NBTTagCompound compoundLong = null;
            NBTTagCompound compoundString = null;
            NBTTagCompound compoundNBT = null;
            NBTTagCompound compoundData = null;

            for (int i = 0; i < tagList.tagCount(); i++) {
                if (tagList.getCompoundTagAt(i).getString("type").equals("boolean")) {
                    compoundBoolean = tagList.getCompoundTagAt(i);
                } else if (tagList.getCompoundTagAt(i).getString("type").equals("byte")) {
                    compoundByte = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type").equals("short")) {
                    compoundShort = tagList.getCompoundTagAt(i);
                } else if (tagList.getCompoundTagAt(i).getString("type").equals("integer")) {
                    compoundInteger = tagList.getCompoundTagAt(i);
                } else if (tagList.getCompoundTagAt(i).getString("type").equals("float")) {
                    compoundFloat = tagList.getCompoundTagAt(i);
                } else if (tagList.getCompoundTagAt(i).getString("type").equals("double")) {
                    compoundDouble = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type").equals("long")) {
                    compoundLong = tagList.getCompoundTagAt(i);
                } else if (tagList.getCompoundTagAt(i).getString("type").equals("string")) {
                    compoundString = tagList.getCompoundTagAt(i);
                } else if (tagList.getCompoundTagAt(i).getString("type").equals("nbt")) {
                    compoundNBT = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type").equals("data")) {
                    compoundData = tagList.getCompoundTagAt(i);
                }
            }

            if (compoundBoolean != null) {
                int size = compoundBoolean.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundBoolean.getString("boolean" + i + "Name");
                    Boolean value = compoundBoolean.getBoolean("boolean" + i + "Value");
                    booleanDataMap.put(name, value);
                }
            }
            if (compoundByte != null) {
                int size = compoundByte.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundByte.getString("byte" + i + "Name");
                    byte value = compoundByte.getByte("byte" + i + "Value");
                    byteDataMap.put(name, value);
                }
            }
            if (compoundShort != null) {
                int size = compoundShort.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundShort.getString("short" + i + "Name");
                    short value = compoundShort.getShort("short" + i + "Value");
                    shortDataMap.put(name, value);
                }
            }
            if (compoundInteger != null) {
                int size = compoundInteger.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundInteger.getString("integer" + i + "Name");
                    int value = compoundInteger.getInteger("integer" + i + "Value");
                    integerDataMap.put(name, value);
                }
            }
            if (compoundFloat != null) {
                int size = compoundFloat.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundFloat.getString("float" + i + "Name");
                    float value = compoundFloat.getFloat("float" + i + "Value");
                    floatDataMap.put(name, value);
                }
            }
            if (compoundDouble != null) {
                int size = compoundDouble.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundDouble.getString("double" + i + "Name");
                    double value = compoundDouble.getDouble("double" + i + "Value");
                    doubleDataMap.put(name, value);
                }
            }
            if (compoundLong != null) {
                int size = compoundLong.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundLong.getString("long" + i + "Name");
                    long value = compoundLong.getLong("long" + i + "Value");
                    longDataMap.put(name, value);
                }
            }
            if (compoundString != null) {
                int size = compoundString.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundString.getString("string" + i + "Name");
                    String value = compoundString.getString("string" + i + "Value");
                    stringDataMap.put(name, value);
                }
            }
            if (compoundNBT != null) {
                int size = compoundNBT.getInteger("size");
                for (int i = 0; i < size; i++) {
                    String name = compoundNBT.getString("nbt" + i + "Name");
                    NBTTagCompound value = compoundNBT.getCompoundTag("nbt" + i + "Name");
                    nbtDataMap.put(name, value);
                }
            }
            if (compoundData != null) {
                int size = compoundData.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundData.getString("data" + i + "Name");
                    NBTTagCompound value = compoundData.getCompoundTag("data" + i + "Value");
                    nbtDataMap.put(name, value);
                }
            }
        }
    }
}
