package seremis.geninfusion.api.soul;

import net.minecraft.item.ItemStack;
import scala.Array;
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

    private EnumAlleleType(Class<?> clzz) {
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
}
