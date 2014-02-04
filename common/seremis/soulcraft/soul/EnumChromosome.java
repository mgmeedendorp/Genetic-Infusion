package seremis.soulcraft.soul;

import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleIntArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.allele.AlleleString;


@SuppressWarnings("unchecked")
public enum EnumChromosome {
    
    MAX_HEALTH(AlleleFloat.class),
    ATTACK_DAMAGE(AlleleFloat.class),
    MOVEMENT_SPEED(AlleleFloat.class),
    BURNS_IN_DAYLIGHT(AlleleBoolean.class),
    DROWNS_IN_WATER(AlleleBoolean.class),
    DROWNS_IN_AIR(AlleleBoolean.class),
    IMMUNE_TO_FIRE(AlleleBoolean.class),
    ITEM_DROPS(AlleleInventory.class),
    EQUIPMENT_DROP_CHANCES(AlleleIntArray.class),
    RARE_ITEM_DROPS(AlleleInventory.class),
    RARE_ITEM_DROP_CHANCE(AlleleInteger.class),
    LIVING_SOUND(AlleleString.class),
    DEATH_SOUND(AlleleString.class),
    HURT_SOUND(AlleleString.class);
    
    public Class<? extends IAllele>[] possibleAlleles;
    
    private EnumChromosome(Class<? extends IAllele>... possibleAlleles) {
        this.possibleAlleles = possibleAlleles;
    }
}
