package seremis.geninfusion.api.soul;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.util.ModelPart;

public enum EnumAlleleType {
    BOOLEAN(Boolean.class),
    BYTE(Byte.class),
    SHORT(Short.class),
    INTEGER(Integer.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    LONG(Long.class),
    STRING(String.class),
    CLASS(Class.class),
    MODELPART(ModelPart.class),
    ITEMSTACK(ItemStack.class),
    BOOLEAN_ARRAY(boolean[].class),
    BYTE_ARRAY(byte[].class),
    SHORT_ARRAY(short[].class),
    INTEGER_ARRAY(int[].class),
    FLOAT_ARRAY(float[].class),
    DOUBLE_ARRAY(double[].class),
    LONG_ARRAY(long[].class),
    STRING_ARRAY(String[].class),
    CLASS_ARRAY(Class[].class),
    ITEMSTACK_ARRAY(ItemStack[].class),
    MODELPART_ARRAY(ModelPart[].class);

    public Class<?> clzz;

    EnumAlleleType(Class<?> clzz) {
        this.clzz = clzz;
    }

    public static EnumAlleleType forClass(Class<?> clzz) {
        for(EnumAlleleType type : values()) {
            if(type.clzz.equals(clzz)) {
                return type;
            }
        }
        return null;
    }

    public NBTTagCompound writeValueToNBT(NBTTagCompound compound, String name, Object value) {
        switch(this) {
            case BOOLEAN: compound.setBoolean(name, (Boolean) value); break;
            case BYTE: compound.setByte(name, (Byte) value); break;
            case SHORT: compound.setShort(name, (Short) value); break;
            case INTEGER: compound.setInteger(name, (Integer) value); break;
            case FLOAT: compound.setFloat(name, (Float) value); break;
            case DOUBLE: compound.setDouble(name, (Double) value); break;
            case LONG: compound.setLong(name, (Long) value); break;
            case STRING: compound.setString(name, (String) value); break;
            case CLASS: compound.setString(name, ((Class<?>) value).getName()); break;
            case MODELPART: compound.setTag(name, ((ModelPart) value).writeToNBT(new NBTTagCompound())); break;
            case ITEMSTACK: compound.setTag(name, ((ItemStack) value).writeToNBT(new NBTTagCompound())); break;
            case BOOLEAN_ARRAY: {
                for(int i = 0; i < ((boolean[]) value).length; i++) {
                    compound.setBoolean(name + "." + i, ((boolean[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((boolean[]) value).length);
                break;
            }
            case BYTE_ARRAY: {
                for(int i = 0; i < ((byte[]) value).length; i++) {
                    compound.setByte(name + "." + i, ((byte[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((byte[]) value).length);
                break;
            }
            case SHORT_ARRAY: {
                for(int i = 0; i < ((short[]) value).length; i++) {
                    compound.setShort(name + "." + i, ((short[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((short[]) value).length);
                break;
            }
            case INTEGER_ARRAY: {
                for(int i = 0; i < ((int[]) value).length; i++) {
                    compound.setInteger(name + "." + i, ((int[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((int[]) value).length);
                break;
            }
            case FLOAT_ARRAY: {
                for(int i = 0; i < ((float[]) value).length; i++) {
                    compound.setFloat(name + "." + i, ((float[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((float[]) value).length);
                break;
            }
            case DOUBLE_ARRAY: {
                for(int i = 0; i < ((double[]) value).length; i++) {
                    compound.setDouble(name + "." + i, ((double[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((double[]) value).length);
                break;
            }
            case LONG_ARRAY: {
                for(int i = 0; i < ((long[]) value).length; i++) {
                    compound.setLong(name + "." + i, ((long[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((long[]) value).length);
                break;
            }
            case STRING_ARRAY: {
                for(int i = 0; i < ((String[]) value).length; i++) {
                    compound.setString(name + "." + i, ((String[]) value)[i]);
                }
                compound.setInteger(name + ".length", ((String[]) value).length);
                break;
            }
            case CLASS_ARRAY: {
                for(int i = 0; i < ((Class<?>[]) value).length; i++) {
                    compound.setString(name + "." + i, ((Class<?>[]) value)[i].getName());
                }
                compound.setInteger(name + ".length", ((Class<?>[]) value).length);
                break;
            }
            case ITEMSTACK_ARRAY: {
                for(int i = 0; i < ((ItemStack[]) value).length; i++) {
                    compound.setTag(name + "." + i, ((ItemStack[]) value)[i].writeToNBT(new NBTTagCompound()));
                }
                compound.setInteger(name + ".length", ((ItemStack[]) value).length);
                break;
            }
            case MODELPART_ARRAY: {
                for(int i = 0; i < ((ModelPart[]) value).length; i++) {
                    compound.setTag(name + "." + i, ((ModelPart[]) value)[i].writeToNBT(new NBTTagCompound()));
                }
                compound.setInteger(name + ".length", ((ModelPart[]) value).length);
                break;
            }
        }
        return compound;
    }

    public Object readValueFromNBT(NBTTagCompound compound, String name) {
        Object result = null;

        switch(this) {
            case BOOLEAN: result = compound.getBoolean(name); break;
            case BYTE: result = compound.getByte(name); break;
            case SHORT: result = compound.getShort(name); break;
            case INTEGER: result = compound.getInteger(name); break;
            case FLOAT: result = compound.getFloat(name); break;
            case DOUBLE: result = compound.getDouble(name); break;
            case LONG: result = compound.getLong(name); break;
            case STRING: result = compound.getString(name); break;
            case CLASS: try{ result = Class.forName(compound.getString(name)); } catch(Exception e) {} break;
            case ITEMSTACK: ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name)); break;
            case MODELPART: ModelPart.fromNBT(compound.getCompoundTag(name)); break;
            case BOOLEAN_ARRAY: {
                int length = compound.getInteger(name + ".length");
                boolean[] out = new boolean[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getBoolean(name + "." + i);
                }
                result = out;
                break;
            }
            case BYTE_ARRAY: {
                int length = compound.getInteger(name + ".length");
                byte[] out = new byte[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getByte(name + "." + i);
                }
                result = out;
                break;
            }
            case SHORT_ARRAY: {
                int length = compound.getInteger(name + ".length");
                short[] out = new short[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getShort(name + "." + i);
                }
                result = out;
                break;
            }
            case INTEGER_ARRAY: {
                int length = compound.getInteger(name + ".length");
                int[] out = new int[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getInteger(name + "." + i);
                }
                result = out;
                break;
            }
            case FLOAT_ARRAY: {
                int length = compound.getInteger(name + ".length");
                float[] out = new float[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getFloat(name + "." + i);
                }
                result = out;
                break;
            }
            case DOUBLE_ARRAY: {
                int length = compound.getInteger(name + ".length");
                double[] out = new double[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getDouble(name + "." + i);
                }
                result = out;
                break;
            }
            case LONG_ARRAY: {
                int length = compound.getInteger(name + ".length");
                long[] out = new long[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getLong(name + "." + i);
                }
                result = out;
                break;
            }
            case STRING_ARRAY: {
                int length = compound.getInteger(name + ".length");
                String[] out = new String[length];
                for(int i = 0; i < length; i++) {
                    out[i] = compound.getString(name + "." + i);
                }
                result = out;
                break;
            }
            case CLASS_ARRAY: {
                int length = compound.getInteger(name + ".length");
                Class<?>[] out = new Class<?>[length];
                try {
                    for(int i = 0; i < length; i++) {
                        out[i] = Class.forName(compound.getString(name + "." + i));
                    }
                } catch (Exception e) {}
                result = out;
                break;
            }
            case ITEMSTACK_ARRAY: {
                int length = compound.getInteger(name + ".length");
                ItemStack[] out = new ItemStack[length];
                for(int i = 0; i < length; i++) {
                    out[i] = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name + "." + i));
                }
                result = out;
                break;
            }
            case MODELPART_ARRAY: {
                int length = compound.getInteger(name + ".length");
                ModelPart[] out = new ModelPart[length];
                for(int i = 0; i < length; i++) {
                    out[i] = ModelPart.fromNBT(compound.getCompoundTag(name + "." + i));
                }
                result = out;
                break;
            }
        }
        return result;
    }
}
