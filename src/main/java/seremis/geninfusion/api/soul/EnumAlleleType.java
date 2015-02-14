package seremis.geninfusion.api.soul;

import seremis.geninfusion.soul.allele.*;

public enum EnumAlleleType {

    BOOLEAN(AlleleBoolean.class),
    INTEGER(AlleleInteger.class),
    FLOAT(AlleleFloat.class),
    DOUBLE(AlleleDouble.class),
    STRING(AlleleString.class),
    BOOLEAN_ARRAY(AlleleBoolean.class),
    INT_ARRAY(AlleleIntArray.class),
    FLOAT_ARRAY(AlleleFloatArray.class),
    DOUBLE_ARRAY(AlleleDoubleArray.class),
    STRING_ARRAY(AlleleStringArray.class),
    ITEMSTACK(AlleleItemStack.class),
    INVENTORY(AlleleInventory.class),
    MODEL_PART(AlleleModelPart.class),
    MODEL_PART_ARRAY(AlleleModelPartArray.class),
    ANIMATION_PART(AlleleAnimationPart.class),
    ANIMATION_PART_ARRAY(AlleleAnimationPartArray.class),
    CLASS(AlleleClass.class),
    CLASS_ARRAY(AlleleClassArray.class);

    public Class<? extends IAllele> clazz;

    private EnumAlleleType(Class<? extends IAllele> clazz) {
        this.clazz = clazz;
    }
}
