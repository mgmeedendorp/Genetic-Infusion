package seremis.soulcraft.soul.handler;

import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleString;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;
import net.minecraft.entity.EntityLiving;

public class ChromosomeHandlerSounds extends EntityEventHandler {

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
