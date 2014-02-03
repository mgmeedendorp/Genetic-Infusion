package seremis.soulcraft.soul.actions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleString;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeSounds extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {}

    @Override
    public void onUpdate(IEntitySoulCustom entity) {}

    @Override
    public void onInteract(IEntitySoulCustom entity, EntityPlayer player) {}

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {}

    @Override
    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {}

    @Override
    public void onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage) {}

    @Override
    public void onSpawnWithEgg(IEntitySoulCustom entity, EntityLivingData data) {}

    @Override
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {
        switch(name) {
            case "sound.living" : {
                String sound = ((AlleleString)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.LIVING_SOUND).getActive()).value;
                entity.playSound(sound, volume, pitch);
                break;
            }
            case "sound.hurt" : {
                String sound = ((AlleleString)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.HURT_SOUND).getActive()).value;
                entity.playSound(sound, volume, pitch);
                break;
            }
            case "sound.death" : {
                String sound = ((AlleleString)SoulHandler.getChromosomeFrom((EntityLiving) entity, EnumChromosome.DEATH_SOUND).getActive()).value;
                entity.playSound(sound, volume, pitch);
                break;
            }
        }
        
    }

}
