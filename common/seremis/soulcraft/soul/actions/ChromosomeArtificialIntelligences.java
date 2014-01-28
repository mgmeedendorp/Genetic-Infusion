package seremis.soulcraft.soul.actions;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleBooleanArray;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleFloatArray;
import seremis.soulcraft.soul.allele.AlleleStringArray;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeArtificialIntelligences extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {
        int taskIndex = 0;
        
        boolean breakDoors = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_BREAK_DOOR).getActive()).value;
        if(breakDoors) {
            entity.getNavigator().setBreakDoors(true);
            entity.getTasks().addTask(taskIndex++, new EntityAIBreakDoor((EntityLiving) entity));
        }
        
        boolean swim = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_SWIM).getActive()).value;
        if(swim) {
            entity.getTasks().addTask(taskIndex++, new EntityAISwimming((EntityLiving) entity));
        }
        
        boolean attackOnCollide = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_ATTACK_ON_COLLIDE).getActive()).value;
        if(attackOnCollide) {
            String[] attackOnCollideTargets = ((AlleleStringArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_ATTACK_ON_COLLIDE_TARGET).getActive()).value;
            float[] attackOnCollideSpeedTowardsTarget = ((AlleleFloatArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_ATTACK_ON_COLLIDE_SPEED_TOWARDS_TARGET).getActive()).value;
            boolean[] attackOnCollideRememberTarget = ((AlleleBooleanArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_ATTACK_ON_COLLIDE_REMEMBER_TARGET).getActive()).value;
            
            for(int i = 0; i < attackOnCollideTargets.length; i++) {
                try {
                    entity.getTasks().addTask(taskIndex++, new EntityAIAttackOnCollide((EntityCreature) entity, Class.forName(attackOnCollideTargets[i]), attackOnCollideSpeedTowardsTarget[i], attackOnCollideRememberTarget[i]));
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        
        boolean avoidEntity = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_AVOID_ENTITY).getActive()).value;
        if(avoidEntity) {
            String[] avoidEntityTargets = ((AlleleStringArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_AVOID_ENTITY_TARGET).getActive()).value;
            float[] avoidEntityAvoidDistance = ((AlleleFloatArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_AVOID_ENTITY_AVOID_DISTANCE).getActive()).value;
            float[] avoidEntitySpeedFar = ((AlleleFloatArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_AVOID_ENTITY_AVOID_SPEED_FAR).getActive()).value;
            float[] avoidEntitySpeedNear = ((AlleleFloatArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_AVOID_ENTITY_AVOID_SPEED_NEAR).getActive()).value;
            
            for(int i = 0; i < avoidEntityTargets.length; i++) {
                try {
                    entity.getTasks().addTask(taskIndex++, new EntityAIAvoidEntity((EntityCreature) entity, Class.forName(avoidEntityTargets[i]), avoidEntityAvoidDistance[i], avoidEntitySpeedFar[i], avoidEntitySpeedNear[i]));
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        
        boolean controlledByPlayer = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_CONTROLLED_BY_PLAYER).getActive()).value;
        if(controlledByPlayer) {
            float maxSpeed = ((AlleleFloat)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_CONTROLLED_BY_PLAYER_MAX_SPEED).getActive()).value;
            
            entity.getTasks().addTask(taskIndex++, new EntityAIControlledByPlayer((EntityLiving) entity, maxSpeed));
        }
        
        boolean openDoors = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_OPEN_DOORS).getActive()).value;
        if(openDoors) {
            entity.getTasks().addTask(taskIndex++, new EntityAIOpenDoor((EntityLiving) entity, true));
        }
        
        boolean eatGrass = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_EAT_GRASS).getActive()).value;
        if(eatGrass) {
            entity.getTasks().addTask(taskIndex++, new EntityAIEatGrass((EntityLiving) entity));
        }
        
        boolean fleeSun = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_FLEE_SUN).getActive()).value;
        if(fleeSun) {
            float speed = ((AlleleFloat)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_FLEE_SUN_SPEED).getActive()).value;
            
            entity.getTasks().addTask(taskIndex++, new EntityAIFleeSun((EntityCreature) entity, speed));
        }
        
        boolean hurtByTarget = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_HURT_BY_TARGET).getActive()).value;
        if(hurtByTarget) {
            boolean getHelp = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_HURT_BY_TARGET_GET_HELP).getActive()).value;
            
            entity.getTasks().addTask(taskIndex++, new EntityAIHurtByTarget((EntityCreature) entity, getHelp));
        }
        
        boolean leapAtTarget = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_LEAP_AT_TARGET).getActive()).value;
        if(leapAtTarget) {
            float motionY = ((AlleleFloat)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_LEAP_AT_TARGET_MOTION_Y).getActive()).value;
            
            entity.getTasks().addTask(taskIndex++, new EntityAILeapAtTarget((EntityLiving) entity, motionY));
        }
        
        boolean lookIdle = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_LOOK_IDLE).getActive()).value;
        if(lookIdle) {
            entity.getTasks().addTask(taskIndex++, new EntityAILookIdle((EntityLiving) entity));
        }
        
        boolean ocelotAttack = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_OCELOT_ATTACK).getActive()).value;
        if(ocelotAttack) {
            entity.getTasks().addTask(taskIndex++, new EntityAIOcelotAttack((EntityLiving) entity));
        }
        
        boolean watchClosest = ((AlleleBoolean)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_WATCH_CLOSEST).getActive()).value;
        if(watchClosest) {
            String[] targets = ((AlleleStringArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_WATCH_CLOSEST_TARGET).getActive()).value;
            float[] maxTargetDistance = ((AlleleFloatArray)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.AI_WATCH_CLOSEST_MAXIMAL_TARGET_DISTANCE).getActive()).value;
            
            for(int i = 0; i < targets.length; i++) {
                try {
                    entity.getTasks().addTask(taskIndex++, new EntityAIWatchClosest((EntityCreature) entity, Class.forName(targets[i]), maxTargetDistance[i]));
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {}

    @Override
    public void onInteract(IEntitySoulCustom entity, EntityPlayer player) {}

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {}

    @Override
    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {}

    @Override
    public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage) {
        return true;
    }

    @Override
    public void onSpawnWithEgg(IEntitySoulCustom entity, EntityLivingData data) {}

    @Override
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {}

}
