package seremis.soulcraft.soul;

import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleIntArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.allele.AlleleString;


public enum EnumChromosome {
    
    IS_TEMPLATE_GENOME(AlleleBoolean.class), 
    MAX_HEALTH(AlleleFloat.class),
    BURNS_IN_DAYLIGHT(AlleleBoolean.class),
    ITEM_DROPS(AlleleInventory.class),
    EQUIPMENT_DROP_CHANCES(AlleleIntArray.class),
    RARE_ITEM_DROP_CHANCE(AlleleInteger.class),
    RARE_ITEM_DROPS(AlleleInventory.class),
    LIVING_SOUND(AlleleString.class),
    STEP_SOUND(AlleleString.class),
    DEATH_SOUND(AlleleString.class),
    HURT_SOUND(AlleleString.class),
    AI_CAN_BREAK_DOORS(AlleleBoolean.class),
    AI_SWIM(AlleleBoolean.class),
    AI_ATTACK_ON_COLLIDE(AlleleBoolean.class),
    AI_ATTACK_ON_COLLIDE_TARGET(AlleleString.class), //Class.forName("classStringName"); 
    AI_ATTACK_ON_COLLIDE_SPEED_TOWARDS_TARGET(AlleleFloat.class),
    AI_ATTACK_ON_COLLIDE_REMEMBER_TARGET(AlleleBoolean.class);
    
    public Class<? extends IAllele>[] possibleAlleles;
    
    private EnumChromosome(Class<? extends IAllele>... possibleAlleles) {
        this.possibleAlleles = possibleAlleles;
    }
}
