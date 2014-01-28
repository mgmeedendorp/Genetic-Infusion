package seremis.soulcraft.soul;

import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleBooleanArray;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleFloatArray;
import seremis.soulcraft.soul.allele.AlleleIntArray;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleInventory;
import seremis.soulcraft.soul.allele.AlleleString;
import seremis.soulcraft.soul.allele.AlleleStringArray;


@SuppressWarnings("unchecked")
public enum EnumChromosome {
    
    IS_TEMPLATE_GENOME(AlleleBoolean.class), 
    MAX_HEALTH(AlleleFloat.class),
    BURNS_IN_DAYLIGHT(AlleleBoolean.class),
    ITEM_DROPS(AlleleInventory.class),
    EQUIPMENT_DROP_CHANCES(AlleleIntArray.class),
    RARE_ITEM_DROP_CHANCE(AlleleInteger.class),
    RARE_ITEM_DROPS(AlleleInventory.class),
    LIVING_SOUND(AlleleString.class),
    DEATH_SOUND(AlleleString.class),
    HURT_SOUND(AlleleString.class),
    AI_BREAK_DOOR(AlleleBoolean.class),
    AI_SWIM(AlleleBoolean.class),
    AI_ATTACK_ON_COLLIDE(AlleleBoolean.class),
    AI_ATTACK_ON_COLLIDE_TARGET(AlleleStringArray.class), //Class.forName("classStringName"); 
    AI_ATTACK_ON_COLLIDE_SPEED_TOWARDS_TARGET(AlleleFloatArray.class),
    AI_ATTACK_ON_COLLIDE_REMEMBER_TARGET(AlleleBooleanArray.class),
    AI_AVOID_ENTITY(AlleleBoolean.class),
    AI_AVOID_ENTITY_TARGET(AlleleStringArray.class),
    AI_AVOID_ENTITY_AVOID_DISTANCE(AlleleFloatArray.class),
    AI_AVOID_ENTITY_AVOID_SPEED_FAR(AlleleFloatArray.class),
    AI_AVOID_ENTITY_AVOID_SPEED_NEAR(AlleleFloatArray.class),
    AI_CONTROLLED_BY_PLAYER(AlleleBoolean.class),
    AI_CONTROLLED_BY_PLAYER_MAX_SPEED(AlleleFloat.class),
    AI_OPEN_DOORS(AlleleBoolean.class),
    AI_EAT_GRASS(AlleleBoolean.class),
    AI_FLEE_SUN(AlleleBoolean.class),
    AI_FLEE_SUN_SPEED(AlleleFloat.class),
    AI_HURT_BY_TARGET(AlleleBoolean.class),
    AI_HURT_BY_TARGET_GET_HELP(AlleleBoolean.class),
    AI_LEAP_AT_TARGET(AlleleBoolean.class),
    AI_LEAP_AT_TARGET_MOTION_Y(AlleleFloat.class),
    AI_LOOK_IDLE(AlleleBoolean.class),
    AI_OCELOT_ATTACK(AlleleBoolean.class),
    AI_WATCH_CLOSEST(AlleleBoolean.class),
    AI_WATCH_CLOSEST_TARGET(AlleleStringArray.class),
    AI_WATCH_CLOSEST_MAXIMAL_TARGET_DISTANCE(AlleleFloatArray.class);
    
    public Class<? extends IAllele>[] possibleAlleles;
    
    private EnumChromosome(Class<? extends IAllele>... possibleAlleles) {
        this.possibleAlleles = possibleAlleles;
    }
}
