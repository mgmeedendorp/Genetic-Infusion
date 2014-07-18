package seremis.geninfusion.soul.allele;

import seremis.geninfusion.api.soul.IAllele;

public enum EnumAlleleType {

    BOOLEAN(AlleleBoolean.class), 
    INTEGER(AlleleInteger.class), 
    FLOAT(AlleleFloat.class),
    STRING(AlleleString.class), 
    BOOLEAN_ARRAY(AlleleBoolean.class), 
    INT_ARRAY(AlleleIntArray.class),
    FLOAT_ARRAY(AlleleFloatArray.class), 
    STRING_ARRAY(AlleleStringArray.class), 
    ITEMSTACK(AlleleItemStack.class), 
    INVENTORY(AlleleInventory.class);
    
    public Class<? extends IAllele> clazz;
    
    private EnumAlleleType(Class<? extends IAllele> clazz) {
        this.clazz = clazz;
    }
}
