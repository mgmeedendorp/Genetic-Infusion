package seremis.soulcraft.soul;

import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleFloatArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.allele.AlleleString;


@SuppressWarnings("unchecked")
public enum EnumChromosome {
    
    MAX_HEALTH(AlleleFloat.class),
    INVULNERABLE(AlleleBoolean.class),
    ATTACK_DAMAGE(AlleleFloat.class),
    MOVEMENT_SPEED(AlleleFloat.class),
    BURNS_IN_DAYLIGHT(AlleleBoolean.class),
    DROWNS_IN_WATER(AlleleBoolean.class),
    DROWNS_IN_AIR(AlleleBoolean.class),
    IMMUNE_TO_FIRE(AlleleBoolean.class),
    MAX_HURT_TIME(AlleleInteger.class),
    MAX_HURT_RESISTANT_TIME(AlleleInteger.class),
    ITEM_DROPS(AlleleInventory.class),
    EQUIPMENT_DROP_CHANCES(AlleleFloatArray.class),
    RARE_ITEM_DROPS(AlleleInventory.class),
    LIVING_SOUND(AlleleString.class),
    DEATH_SOUND(AlleleString.class),
    HURT_SOUND(AlleleString.class),
    SOUND_VOLUME(AlleleFloat.class),
    CREATURE_ATTRIBUTE(AlleleInteger.class),
    TELEPORT_TIME_IN_PORTAL(AlleleInteger.class),
    PORTAL_COOLDOWN(AlleleInteger.class);
    
    public Class<? extends IAllele>[] possibleAlleles;
    
    private EnumChromosome(Class<? extends IAllele>... possibleAlleles) {
        this.possibleAlleles = possibleAlleles;
    }
}
