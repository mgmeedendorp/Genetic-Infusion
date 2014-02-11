package seremis.soulcraft.soul.handler;

import net.minecraft.entity.SharedMonsterAttributes;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeHandlerEntityAttributes extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {
        float maxHealth = ((AlleleFloat)SoulHandler.getChromosomeFrom(entity, EnumChromosome.MAX_HEALTH).getActive()).value;
        entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(maxHealth);
        
        float attackDamage = ((AlleleFloat)SoulHandler.getChromosomeFrom(entity, EnumChromosome.ATTACK_DAMAGE).getActive()).value;
        entity.getAttributeMap().func_111150_b(SharedMonsterAttributes.attackDamage);
        entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(attackDamage);
        
        float movementSpeed = ((AlleleFloat)SoulHandler.getChromosomeFrom(entity, EnumChromosome.MOVEMENT_SPEED).getActive()).value;
        entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(movementSpeed);
    }
}
