package seremis.soulcraft.soul.actions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.IChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeMaxHealth extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {
        IChromosome chromosome = SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.MAX_HEALTH);
        entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(((AlleleFloat)chromosome.getPrimary()).value);
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
