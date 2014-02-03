package seremis.soulcraft.soul.actions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeHandlerFluids extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {}

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean drownsInWater = ((AlleleBoolean)SoulHandler.getSoulFrom((EntityLiving) entity).getChromosomes()[EnumChromosome.DROWNS_IN_WATER.ordinal()].getActive()).value;
        
        if(drownsInWater) {
            int air = entity.getAir();
            if (entity.isEntityAlive() && entity.isInWater()) {
                --air;
                
                if(air == -20) {
                    entity.setAir(0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }
        
        boolean drownsInAir = ((AlleleBoolean)SoulHandler.getSoulFrom((EntityLiving) entity).getChromosomes()[EnumChromosome.DROWNS_IN_AIR.ordinal()].getActive()).value;
        
        if(drownsInAir) {
            int air = entity.getAir();
            if (entity.isEntityAlive() && !entity.isInWater() && !entity.handleLavaMovement()) {
                --air;
                
                if(air == -20) {
                    entity.setAir(0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }
    }

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
    public void playSound(IEntitySoulCustom entity, String name, float volume, float pitch) {}

}
