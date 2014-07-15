package seremis.soulcraft.soul.traits;

import net.minecraft.entity.Entity;
import seremis.soulcraft.api.soul.IEntitySoulCustom;
import seremis.soulcraft.soul.Trait;

/**
 * @author Seremis
 */
public class TraitSounds extends Trait {

    @Override
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {
        entity.getWorld().playSoundAtEntity((Entity) entity, name, volume, pitch);
    }
}
